package com.infy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRentalRequest {
    private Long clientId;
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
}
