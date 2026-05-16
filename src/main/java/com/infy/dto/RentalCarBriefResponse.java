package com.infy.dto;

import lombok.Data;

@Data
public class RentalCarBriefResponse {
    private Long carId;
    private String brand;
    private String model;
    private Double price;
    private Double deposit;
}
