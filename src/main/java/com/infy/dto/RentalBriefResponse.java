package com.infy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalBriefResponse {
    private Long id;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;

    private Long clientId;
    private String clientFullName;
    private String clientLogin;

    private Long employeeId;
    private String employeeFullName;
    private String employeeLogin;
}