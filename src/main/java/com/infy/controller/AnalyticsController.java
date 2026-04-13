package com.infy.controller;

import com.infy.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/rentals")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getRentalsReport() {
        return ResponseEntity.ok(analyticsService.getRentalsReport());
    }

    @GetMapping("/clients/by-brand")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getClientsByCarBrand(
            @RequestParam String brand) {
        return ResponseEntity.ok(analyticsService.getClientsByCarBrand(brand));
    }

    @GetMapping("/clients/top")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getTopClients(
            @RequestParam(defaultValue = "3") int minRentals) {
        return ResponseEntity.ok(analyticsService.getTopClientsByRentals(minRentals));
    }

    @GetMapping("/cars")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getCarsReport() {
        return ResponseEntity.ok(analyticsService.getCarsRentalReport());
    }
}