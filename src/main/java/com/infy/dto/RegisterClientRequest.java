package com.infy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterClientRequest  {
    private String login;
    private String password;
    private String fullName;
    private String phone;
    private String driverLicense;
    private LocalDate birthDate;
    private String personalEmail;
}
