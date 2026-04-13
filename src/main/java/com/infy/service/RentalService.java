package com.infy.service;

import com.infy.entity.Rental;
import com.infy.repo.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    @Transactional(readOnly = true)
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Rental> findAllWithClientAndEmployee() {
        return rentalRepository.findAllWithClientAndEmployee();
    }

    @Transactional(readOnly = true)
    public Optional<Rental> findById(Long id) {
        return rentalRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Rental> findByIdWithDetails(Long id) {
        List<Rental> rentals = rentalRepository.findByIdWithDetails(id);
        return rentals.isEmpty() ? Optional.empty() : Optional.of(rentals.get(0));
    }

    @Transactional
    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Transactional
    public void deleteById(Long id) {
        rentalRepository.deleteById(id);
    }
}
