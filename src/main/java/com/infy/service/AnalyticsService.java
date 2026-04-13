package com.infy.service;

import com.infy.repo.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> getRentalsReport() {
        List<Object[]> data = analyticsRepository.getRentalsReport();
        return buildResponse(data, "Аренды");
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getClientsByCarBrand(String brand) {
        List<Object[]> data = analyticsRepository.getClientsByCarBrand(brand);
        return buildResponse(data, "Клиенты с арендами марки " + brand);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getTopClientsByRentals(int minRentals) {
        List<Object[]> data = analyticsRepository.getTopClientsByRentals(minRentals);
        return buildResponse(data, "Топ клиентов (мин. аренд: " + minRentals + ")");
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getCarsRentalReport() {
        List<Object[]> data = analyticsRepository.getCarsRentalReport();
        return buildResponse(data, "Статистика по автомобилям");
    }

    private Map<String, Object> buildResponse(List<Object[]> data, String reportName) {
        Map<String, Object> response = new HashMap<>();
        if (data.isEmpty()) {
            response.put("message", "Данных нет");
            response.put("data", List.of());
        } else {
            response.put("message", "Данных нет");
            response.put("data", data);
        }
        response.put("report", reportName);
        response.put("count", data.size());
        return response;
    }
}