package com.infy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CarCreateUpdateDTO {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private Double price;
    private Double deposit;
    private String vin;
    private LocalDate registrationDate;
    private BigDecimal engineVolume;
    private String color;
    private LocalDate insuranceValidUntil; // Страховка до
    private LocalDate inspectionValidUntil;
}
