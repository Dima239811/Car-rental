package com.infy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateRentalRequest {
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
}
