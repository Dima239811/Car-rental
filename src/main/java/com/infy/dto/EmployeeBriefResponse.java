package com.infy.dto;

import lombok.Data;

@Data
public class EmployeeBriefResponse {
    private Long id;
    private Long userId;
    private String login;
    private String fullName;
    private String phone;
    private String position;
    private Double salary;
    private String department;
    private String officeNumber;
    private String workEmail;
}
