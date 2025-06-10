package com.example.sto.service;


import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.dto.CreateServiceRequestDto;
import com.example.sto.dto.ServiceRequestDto;
import com.example.sto.dto.StatusChangeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceRequestService {

    ServiceRequestDto createServiceRequest(CreateServiceRequestDto createDto);
    ServiceRequestDto getServiceRequest(Long id);
    ServiceRequestDto getServiceRequestByNumber(String requestNumber);
    Page<ServiceRequestDto> getServiceRequestsByClient(Long clientId, Pageable pageable);
    Page<ServiceRequestDto> getServiceRequestsByStatus(RequestStatus status, Pageable pageable);
    List<ServiceRequestDto> getAllServiceRequests();
    ServiceRequestDto changeStatus(Long id, StatusChangeDto statusChangeDto);
    void deleteServiceRequest(Long id);
}
