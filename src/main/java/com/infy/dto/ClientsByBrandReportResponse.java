package com.infy.dto;

import java.util.List;

public record ClientsByBrandReportResponse(
        List<ClientBrandAnalyticsResponse> data,
        String report,
        int count,
        String message
) {
}
