package com.infy.dto;

import java.time.LocalDate;

public record ClientProfileResponse(
        String login,
        String fullName,
        String phone,
        String driverLicense,
        LocalDate birthDate,
        String personalEmail
) {}
