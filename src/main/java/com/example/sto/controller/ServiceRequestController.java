package com.example.sto.controller;

import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.dto.CreateServiceRequestDto;
import com.example.sto.dto.ServiceRequestDto;
import com.example.sto.dto.StatusChangeDto;
import com.example.sto.service.ServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/service-requests")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @PostMapping
    public ResponseEntity<ServiceRequestDto> createServiceRequest(
            @Valid @RequestBody CreateServiceRequestDto createDto) {
        log.info("Creating service request for client: {}", createDto.getClientId()); /*show also email*/
        ServiceRequestDto created = serviceRequestService.createServiceRequest(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestDto> getServiceRequest(@PathVariable Long id) {
        ServiceRequestDto serviceRequest = serviceRequestService.getServiceRequest(id);
        return ResponseEntity.ok(serviceRequest);
    }

    @GetMapping("/number/{requestNumber}")
    public ResponseEntity<ServiceRequestDto> getServiceRequestByNumber(@PathVariable String requestNumber) {
        ServiceRequestDto serviceRequest = serviceRequestService.getServiceRequestByNumber(requestNumber);
        return ResponseEntity.ok(serviceRequest);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<ServiceRequestDto>> getServiceRequestsByClient(
            @PathVariable Long clientId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ServiceRequestDto> requests = serviceRequestService.getServiceRequestsByClient(clientId, pageable);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ServiceRequestDto>> getServiceRequestsByStatus(
            @PathVariable RequestStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ServiceRequestDto> requests = serviceRequestService.getServiceRequestsByStatus(status, pageable);
        return ResponseEntity.ok(requests);
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestDto>> getAllServiceRequests() {
        List<ServiceRequestDto> requests = serviceRequestService.getAllServiceRequests();
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceRequestDto> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusChangeDto statusChangeDto) {
        ServiceRequestDto updated = serviceRequestService.changeStatus(id, statusChangeDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable Long id) {
        serviceRequestService.deleteServiceRequest(id);
        return ResponseEntity.noContent().build();
    }
}

