package com.example.sto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceRequestDto {
    private String description;

    @NotBlank(message = "Car model is required")
    private String carModel;

    @NotBlank(message = "Car year is required")
    private String carYear;

    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    private String mechanicNotes;
}
