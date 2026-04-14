package com.infy.controller;

import com.infy.dto.CreateRentalRequest;
import com.infy.dto.RentalBriefResponse;
import com.infy.entity.Rental;
import com.infy.exception.ResourceNotFoundException;
import com.infy.mapper.RentalMapper;
import com.infy.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    private final RentalMapper rentalMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<RentalBriefResponse>> getAll() {
        return ResponseEntity.ok(rentalMapper.toBriefResponseList(rentalService.findAllWithClientAndEmployee()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RentalBriefResponse> getById(@PathVariable Long id) {
        return rentalService.findByIdWithDetails(id)
                .map(rentalMapper::toBriefResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Аренда с ID " + id + " не найдена"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RentalBriefResponse> create(@RequestBody CreateRentalRequest rental) {
        Rental rental1 = rentalService.save(rental);
        return ResponseEntity.ok(rentalMapper.toBriefResponse(rental1));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<RentalBriefResponse> update(@PathVariable Long id, @RequestBody CreateRentalRequest rental) {
        Rental rental1 = rentalService.update(id, rental);
        return ResponseEntity.ok(rentalMapper.toBriefResponse(rental1));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rentalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
