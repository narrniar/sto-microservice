package com.example.sto.domain.events;

import com.example.sto.domain.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusChangeEvent {
    private Long serviceRequestId;
    private String requestNumber;
    private RequestStatus fromStatus;
    private RequestStatus toStatus;
    private String changedBy;
    private String reason;
    private String notes;
    private String clientEmail;
    private String clientPhone;
    private LocalDateTime timestamp;
}
