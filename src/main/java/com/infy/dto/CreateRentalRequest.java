package com.infy.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateRentalRequest {
    private Long clientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
    private List<Long> carIds;
    private double discount;
}
