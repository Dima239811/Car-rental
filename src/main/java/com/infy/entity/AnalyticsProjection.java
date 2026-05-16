package com.infy.entity;

public interface AnalyticsProjection {
    Long getTotalRentals();
    Long getTotalClients();
    Double getTotalIncome();
    String getTopClient();
}
