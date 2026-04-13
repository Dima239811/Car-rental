package com.infy.service;

import com.infy.entity.Car;
import com.infy.repo.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    @Transactional(readOnly = true)
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Car> findByVin(String vin) {
        return carRepository.findByVin(vin);
    }

    @Transactional(readOnly = true)
    public List<Car> findByAvailable(Boolean available) {
        return carRepository.findByAvailable(available);
    }

    @Transactional
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
