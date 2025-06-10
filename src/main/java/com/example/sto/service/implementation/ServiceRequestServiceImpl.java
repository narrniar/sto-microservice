package com.example.sto.service.implementation;


import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.domain.events.StatusChangeEvent;
import com.example.sto.domain.models.Client;
import com.example.sto.domain.models.ServiceRequest;
import com.example.sto.domain.models.StatusHistory;
import com.example.sto.dto.CreateServiceRequestDto;
import com.example.sto.dto.ServiceRequestDto;
import com.example.sto.dto.StatusChangeDto;
import com.example.sto.exception.InvalidStatusTransitionException;
import com.example.sto.exception.ServiceRequestNotFoundException;
import com.example.sto.mapper.ServiceRequestMapper;
import com.example.sto.messaging.StatusChangeProducer;
import com.example.sto.repository.ClientRepository;
import com.example.sto.repository.ServiceRequestRepository;
import com.example.sto.repository.StatusHistoryRepository;
import com.example.sto.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ClientRepository clientRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final ServiceRequestMapper mapper;
    private final StatusChangeProducer statusChangeProducer;

    /*
     * LOG: create service request for CLIENT with ID
     * LOG: found/not found CLIENT with ID
     * GENERATE: Request Number for the Service Request
     * CREATE: ServiceRequest class
     * LOG: create ServiceRequest class
     * SAVE: ServiceRequest class
     * LOG: save ServiceRequest class
     * CREATE: StatusHistory class with initial status
     * LOG: SAVE StatusHistory class
     * LOG: finish createServiceRequest method for CLIENT with ID
     * */
    @Override
    public ServiceRequestDto createServiceRequest(CreateServiceRequestDto createDto) {

        log.info("Creating service request for client ID: {}", createDto.getClientId()); /*show also email*/

        Client client = clientRepository.findById(createDto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + createDto.getClientId()));

        String requestNumber = generateRequestNumber();

        log.info("Generated request number: {}", requestNumber);

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .requestNumber(requestNumber)
                .description(createDto.getDescription())
                .carModel(createDto.getCarModel())
                .carYear(createDto.getCarYear())
                .licensePlate(createDto.getLicensePlate())
                .status(RequestStatus.CREATED)
                .client(client)
                .mechanicNotes(createDto.getMechanicNotes())
                .build();

        ServiceRequest savedRequest = serviceRequestRepository.save(serviceRequest);
        log.info("Service request saved with ID: {}", savedRequest.getId());



        // Create initial status history
        StatusHistory initialHistory = StatusHistory.builder()
                .serviceRequest(savedRequest)
                .fromStatus(null)
                .toStatus(RequestStatus.CREATED)
                .changedBy("SYSTEM")
                .reason("Service request created")
                .build();

        statusHistoryRepository.save(initialHistory);
        log.info("Initial status history created for service request ID: {}", savedRequest.getId());

        log.info("Finish createServiceRequest method for client ID: {}", createDto.getClientId());
        return mapper.toDto(savedRequest);
    }

    private String generateRequestNumber() {
        return "SR-" + LocalDateTime.now().getYear() + "-" +
                String.valueOf(UUID.randomUUID().getMostSignificantBits()).replace("-", "").substring(0, 8).toUpperCase();
    }

    @Override
    @Cacheable(value = "serviceRequests", key = "#id")
    @Transactional(readOnly = true)
    public ServiceRequestDto getServiceRequest(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findByIdWithHistory(id)
                .orElseThrow(() -> new ServiceRequestNotFoundException("Service request not found with ID: " + id));

        return mapper.toDto(serviceRequest);
    }

    @Override
    @Cacheable(value = "serviceRequests", key = "#requestNumber")
    @Transactional(readOnly = true)
    public ServiceRequestDto getServiceRequestByNumber(String requestNumber) {
        ServiceRequest serviceRequest = serviceRequestRepository.findByRequestNumber(requestNumber)
                .orElseThrow(() -> new ServiceRequestNotFoundException("Service request not found with number: " + requestNumber));

        return mapper.toDto(serviceRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestDto> getServiceRequestsByClient(Long clientId, Pageable pageable) {
        Page<ServiceRequest> requests = serviceRequestRepository.findByClientId(clientId, pageable);
        return requests.map(mapper::toDto);
    }

    @Override
    @Cacheable(value = "requestsByStatus", key = "#status")
    @Transactional(readOnly = true)
    public Page<ServiceRequestDto> getServiceRequestsByStatus(RequestStatus status, Pageable pageable) {
        Page<ServiceRequest> requests = serviceRequestRepository.findByStatus(status, pageable);
        return requests.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestDto> getAllServiceRequests() {
        List<ServiceRequest> requests = serviceRequestRepository.findAll();
        return mapper.toDtoList(requests);
    }

    @Override
    @CacheEvict(value = {"serviceRequests", "requestsByStatus"}, allEntries = true)
    public ServiceRequestDto changeStatus(Long id, StatusChangeDto statusChangeDto) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ServiceRequestNotFoundException("Service request not found with ID: " + id));

        RequestStatus currentStatus = serviceRequest.getStatus();
        RequestStatus targetStatus = statusChangeDto.getTargetStatus();

        // Validate status transition
        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new InvalidStatusTransitionException(String.format("Cannot transition from %s to %s", currentStatus, targetStatus));
        }

        // Update status
        serviceRequest.setStatus(targetStatus);

        // Set completion date if completed
        if (targetStatus == RequestStatus.COMPLETED) {
            serviceRequest.setActualCompletionDate(LocalDateTime.now());
        }

        ServiceRequest updatedRequest = serviceRequestRepository.save(serviceRequest);

        // Create status history record
        StatusHistory statusHistory = StatusHistory.builder()
                .serviceRequest(updatedRequest)
                .fromStatus(currentStatus)
                .toStatus(targetStatus)
                .changedBy(statusChangeDto.getChangedBy() != null ? statusChangeDto.getChangedBy() : "SYSTEM")
                .reason(statusChangeDto.getReason())
                .build();

        statusHistoryRepository.save(statusHistory);

        // Publish status change event
        StatusChangeEvent event = StatusChangeEvent.builder()
                .serviceRequestId(updatedRequest.getId())
                .requestNumber(updatedRequest.getRequestNumber())
                .fromStatus(currentStatus)
                .toStatus(targetStatus)
                .changedBy(statusHistory.getChangedBy())
                .reason(statusChangeDto.getReason())
                .clientEmail(updatedRequest.getClient().getEmail())
                .clientPhone(updatedRequest.getClient().getPhoneNumber())
                .timestamp(LocalDateTime.now())
                .build();

        statusChangeProducer.publishStatusChangeEvent(event);

        log.info("Status changed for request {}: {} -> {}",
                updatedRequest.getRequestNumber(), currentStatus, targetStatus);

        return mapper.toDto(updatedRequest);
    }

    @Override
    @CacheEvict(value = {"serviceRequests", "requestsByStatus"}, allEntries = true)
    public void deleteServiceRequest(Long id) {
        if (!serviceRequestRepository.existsById(id)) {
            throw new ServiceRequestNotFoundException("Service request not found with ID: " + id);
        }
        serviceRequestRepository.deleteById(id);
        log.info("Service request deleted with ID: {}", id);
    }
}
