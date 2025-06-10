package com.example.sto.dto;

import com.example.sto.domain.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusChangeDto {
    @NotNull(message = "Target status is required")
    private RequestStatus targetStatus;

    private String reason;
    @NotNull
    private String changedBy;
}
