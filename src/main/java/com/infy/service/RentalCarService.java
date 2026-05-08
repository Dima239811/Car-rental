package com.infy.service;

import com.infy.dto.RentalCarRequest;
import com.infy.entity.Car;
import com.infy.entity.CarRentalId;
import com.infy.entity.Rental;
import com.infy.entity.RentalCar;
import com.infy.repo.RentalCarRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalCarService {
    private final RentalCarRepository rentalCarRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<RentalCar> findAll() {
        return rentalCarRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<RentalCar> findAllWithCarAndRental() {
        return rentalCarRepository.findAllWithCarAndRental();
    }

    @Transactional(readOnly = true)
    public Optional<RentalCar> findById(CarRentalId id) {
        return rentalCarRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<RentalCar> findByRentalId(Long rentalId) {
        return rentalCarRepository.findByRentalIdWithCarAndRental(rentalId);
    }

    @Transactional
    public RentalCar save(RentalCar rentalCar) {
        return rentalCarRepository.save(rentalCar);
    }

    @Transactional
    public RentalCar update(CarRentalId id, RentalCar rentalCar) {
        RentalCar existing = rentalCarRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Связь аренда-авто (carId=" + id.getCarId() + ", rentalId=" + id.getRentalId() + ") не найдена"));

        existing.setCar(rentalCar.getCar());
        existing.setRental(rentalCar.getRental());
        existing.setDiscount(rentalCar.getDiscount());

        return rentalCarRepository.save(existing);
    }

    @Transactional
    public void deleteById(CarRentalId id) {
        rentalCarRepository.deleteById(id);
    }

    public boolean isAvailable(Long carId, LocalDate startDate, LocalDate endDate) {
        List<RentalCar> conflicts = rentalCarRepository.findConflictingRentals(
                carId, startDate, endDate
        );
        return conflicts.isEmpty();
    }

    @Transactional
    public RentalCar createLink(RentalCarRequest dto) {
        CarRentalId id = new CarRentalId(dto.getCarId(), dto.getRentalId());

        RentalCar rentalCar = new RentalCar();
        rentalCar.setId(id);
        rentalCar.setDiscount(dto.getDiscount());

        rentalCar.setCar(entityManager.getReference(Car.class, dto.getCarId()));
        rentalCar.setRental(entityManager.getReference(Rental.class, dto.getRentalId()));

        return rentalCarRepository.save(rentalCar);
    }
}
