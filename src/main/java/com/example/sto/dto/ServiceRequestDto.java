package com.example.sto.dto;

import com.example.sto.domain.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {
    private Long id;
    private String requestNumber;
    private String description;
    private String carModel;
    private String carYear;
    private String licensePlate;
    private RequestStatus status;
    private String estimatedCost;
    private String actualCost;
    private LocalDateTime estimatedCompletionDate;
    private LocalDateTime actualCompletionDate;
    private String mechanicNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<StatusHistoryDto> statusHistory;
    private ClientDto client;


}
