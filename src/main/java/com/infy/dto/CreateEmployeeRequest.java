package com.infy.dto;

import lombok.Data;

@Data
public class CreateEmployeeRequest {
    private String login;
    private String password;
    private String fullName;
    private String phone;
    private String position;
    private Double salary;
    private String department;
    private String officeNumber;
    private String workEmail;
}
