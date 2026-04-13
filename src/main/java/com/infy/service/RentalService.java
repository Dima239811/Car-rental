package com.infy.service;

import com.infy.entity.Rental;
import com.infy.enums.RentalStatus;
import com.infy.exception.BadRequestException;
import com.infy.exception.ResourceNotFoundException;
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
    public Rental update(Long id, Rental rental) {
        Rental existing = rentalRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Аренда с ID " + id + " не найдена"));

        existing.setStatus(rental.getStatus());
        existing.setStartDate(rental.getStartDate());
        existing.setEndDate(rental.getEndDate());
        existing.setComment(rental.getComment());
        existing.setClient(rental.getClient());
        existing.setEmployee(rental.getEmployee());

        return rentalRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Аренда с ID " + id + " не найдена"));

        if (rental.getStatus() == RentalStatus.ACTIVE) {
            throw new BadRequestException("Невозможно удалить аренду с ID " + id + ": аренда ещё активна");
        }

        rentalRepository.deleteById(id);
    }
}
