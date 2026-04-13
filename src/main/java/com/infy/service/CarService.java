package com.infy.service;

import com.infy.entity.Car;
import com.infy.entity.RentalCar;
import com.infy.enums.RentalStatus;
import com.infy.exception.BadRequestException;
import com.infy.repo.CarRepository;
import com.infy.repo.RentalCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    private final RentalCarRepository rentalCarRepository;

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
    public Car update(Long id, Car car) {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Автомобиль с ID " + id + " не найден"));

        existing.setBrand(car.getBrand());
        existing.setModel(car.getModel());
        existing.setYear(car.getYear());
        existing.setPrice(car.getPrice());
        existing.setDeposit(car.getDeposit());
        existing.setAvailable(car.getAvailable());
        existing.setVin(car.getVin());
        existing.setRegistrationDate(car.getRegistrationDate());
        existing.setEngineVolume(car.getEngineVolume());
        existing.setColor(car.getColor());
        existing.setInsuranceValidUntil(car.getInsuranceValidUntil());
        existing.setInspectionValidUntil(car.getInspectionValidUntil());

        return carRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        List<RentalCar> activeLinks = rentalCarRepository.findByCarIdAndRentalStatusIn(id,
                List.of(RentalStatus.PENDING, RentalStatus.CONFIRMED, RentalStatus.ACTIVE));
        if (!activeLinks.isEmpty()) {
            throw new BadRequestException("Невозможно удалить автомобиль с ID " + id + ": есть активные аренды");
        }
        carRepository.deleteById(id);
    }
}
