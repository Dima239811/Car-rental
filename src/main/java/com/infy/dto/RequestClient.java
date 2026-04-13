package com.infy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestClient {
    private String driverLicense;
    private LocalDate birthDate;
    private String personalEmail;
    private int rentCount;
    private long userId;
}
