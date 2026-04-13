package com.infy.controller;

import com.infy.entity.CarRentalId;
import com.infy.entity.RentalCar;
import com.infy.service.RentalCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental-cars")
@RequiredArgsConstructor
public class RentalCarController {
    private final RentalCarService rentalCarService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<RentalCar>> getAll() {
        return ResponseEntity.ok(rentalCarService.findAllWithCarAndRental());
    }

    @GetMapping("/rental/{rentalId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<RentalCar>> getByRentalId(@PathVariable Long rentalId) {
        return ResponseEntity.ok(rentalCarService.findByRentalId(rentalId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RentalCar> create(@RequestBody RentalCar rentalCar) {
        return ResponseEntity.ok(rentalCarService.save(rentalCar));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> delete(@RequestBody CarRentalId id) {
        rentalCarService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
