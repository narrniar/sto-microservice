package com.example.sto.dto;

import com.example.sto.domain.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistoryDto {
    @NotNull
    private RequestStatus fromStatus;
    @NotNull
    private RequestStatus toStatus;
    private String changedBy;
    private String reason;
    private LocalDateTime createdAt;
}
