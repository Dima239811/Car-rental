package com.infy.service;

import com.infy.dto.AnalyticsByPeriodResponse;
import com.infy.dto.ClientBrandAnalyticsResponse;
import com.infy.dto.ClientsByBrandReportResponse;
import com.infy.entity.AnalyticsProjection;
import com.infy.repo.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

//    @Transactional(readOnly = true)
//    public Map<String, Object> getRentalsReport() {
//        List<Object[]> data = analyticsRepository.getRentalsReport();
//        return buildResponse(data, "Аренды");
//    }

    @Transactional(readOnly = true)
    public ClientsByBrandReportResponse getClientsByCarBrand(String brand) {

        List<Object[]> rows = analyticsRepository.getClientsByCarBrand(brand);

        List<ClientBrandAnalyticsResponse> clients = rows.stream()
                .map(row -> new ClientBrandAnalyticsResponse(
                        ((Number) row[0]).longValue(),
                        row[1].toString(),
                        row[2].toString(),
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue()
                ))
                .toList();

        return new ClientsByBrandReportResponse(
                clients,
                "Клиенты c кол-во аренд бренда:  " + brand,
                clients.size(),
                clients.isEmpty() ? "Данных нет" : "OK"
        );
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getTopClientsByRentals(int minRentals) {
        List<Object[]> data = analyticsRepository.getTopClientsByRentals(minRentals);

        List<Map<String, Object>> clients = data.stream()
                .map(row -> {
                    Map<String, Object> client = new HashMap<>();
                    client.put("fullName", row[2].toString());
                    client.put("rentalsCount", ((Number) row[3]).intValue());
                    return client;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("clients", clients);
        response.put("count", clients.size());

        if (clients.isEmpty()) {
            response.put("message", "Данных нет");
        } else {
            response.put("message", "OK");
        }

        return response;
    }


    @Transactional(readOnly = true)
    public Map<String, Object> getCarsRentalReport() {

        List<Object[]> data = analyticsRepository.getCarsRentalReport();

        List<String> brands = data.stream()
                .map(row -> row[0].toString())
                .toList();

        List<Long> counts = data.stream()
                .map(row -> ((Number) row[1]).longValue())
                .toList();

        List<Double> avgDiscounts = data.stream()
                .map(row -> ((Number) row[2]).doubleValue())
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("brands", brands);
        response.put("counts", counts);
        response.put("avgDiscounts", avgDiscounts);
        response.put("totalCars", data.size());

        response.put("message", data.isEmpty() ? "Данных нет" : "OK");

        return response;
    }

    @Transactional(readOnly = true)
    public AnalyticsByPeriodResponse getAnalyticsByPeriod(
            LocalDate startDate,
            LocalDate endDate
    ) {

        AnalyticsProjection res = analyticsRepository.getAnalyticsByPeriod(startDate, endDate);
        return new AnalyticsByPeriodResponse(
                res.getTotalRentals(),
                res.getTotalClients(),
                res.getTotalIncome(),
                res.getTopClient()
        );
    }
}