package com.infy.dto;

import java.time.LocalDate;

public record ClientProfileResponse(
        Long id,
        String login,
        String fullName,
        String phone,
        String driverLicense,
        LocalDate birthDate,
        String personalEmail
) {}
