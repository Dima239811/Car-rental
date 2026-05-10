package com.infy.dto;

import lombok.Data;

@Data
public class EmployeeUpdateRequest {
    private Long id;
    private String position;
    private Double salary;
    private String department;
    private String officeNumber;
    private String workEmail;
}
