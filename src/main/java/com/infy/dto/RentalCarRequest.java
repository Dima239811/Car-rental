package com.infy.dto;

import lombok.Data;

@Data
public class RentalCarRequest {
    private Long carId;
    private Long rentalId;
    private Double discount;
}
