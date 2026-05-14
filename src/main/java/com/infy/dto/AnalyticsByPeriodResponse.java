package com.infy.dto;

public record AnalyticsByPeriodResponse(
        Long totalRentals,
        Long totalClients,
        Double totalIncome,
        String topClient
) {}