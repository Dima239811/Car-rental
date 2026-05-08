package com.infy.dto;

import com.infy.enums.Role;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class UserProfileResponse {
    // Данные из сущности User
    private Long userId;
    private String login;
    private String fullName;
    private String phone;
    private Role role;

    // Данные из сущности Client
    private Long clientId;
    private String driverLicense;
    private LocalDate birthDate;
    private String personalEmail;
    private int rentCount;
}
