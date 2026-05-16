package com.infy.controller;

import com.infy.dto.CarCreateUpdateDTO;
import com.infy.entity.Car;
import com.infy.exception.ResourceNotFoundException;
import com.infy.mapper.CarMapper;
import com.infy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLIENT')")
    public ResponseEntity<List<CarCreateUpdateDTO>> getAll() {
        return ResponseEntity.ok(carMapper.toCarCreateUpdateDTOList(carService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLIENT')")
    public ResponseEntity<CarCreateUpdateDTO> getById(@PathVariable Long id) {
        return carService.findById(id)
                .map(car -> {
                    CarCreateUpdateDTO dto = carMapper.toCarCreateUpdateDTO(car);
                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Автомобиль с ID " + id + " не найден"));
    }


    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLIENT')")
    public ResponseEntity<List<Car>> getAvailable() {
        return ResponseEntity.ok(carService.findByAvailable(true));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarCreateUpdateDTO> create(@RequestBody CarCreateUpdateDTO car) {
        return ResponseEntity.ok(carMapper.toCarCreateUpdateDTO(carService.save(car)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarCreateUpdateDTO> update(@PathVariable Long id, @RequestBody CarCreateUpdateDTO car) {
        return ResponseEntity.ok(carMapper.toCarCreateUpdateDTO(carService.update(id, car)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
