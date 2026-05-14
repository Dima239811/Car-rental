package com.infy.dto;

public record ClientBrandAnalyticsResponse(
        Long id,
        String driverLicense,
        String fullName,
        Long totalRentals,
        Long brandRentals
) {
}
