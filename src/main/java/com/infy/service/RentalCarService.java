package com.infy.service;

import com.infy.entity.CarRentalId;
import com.infy.entity.RentalCar;
import com.infy.repo.RentalCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalCarService {
    private final RentalCarRepository rentalCarRepository;

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
    public void deleteById(CarRentalId id) {
        rentalCarRepository.deleteById(id);
    }
}
