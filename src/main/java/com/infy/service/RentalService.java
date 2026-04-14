package com.infy.service;

import com.infy.dto.CreateRentalRequest;
import com.infy.entity.Client;
import com.infy.entity.Employee;
import com.infy.entity.Rental;
import com.infy.enums.RentalStatus;
import com.infy.exception.BadRequestException;
import com.infy.exception.ResourceNotFoundException;
import com.infy.repo.ClientRepository;
import com.infy.repo.EmployeeRepository;
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

    private final ClientRepository clientRepository;

    private final EmployeeRepository employeeRepository;

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
    public Rental save(CreateRentalRequest rental) {
        Client client = clientRepository.findById(rental.getClientId()).orElseThrow(
                () -> new com.infy.exception.ResourceNotFoundException("Клиент с ID " + rental.getClientId() + " не найден")
                );

        Employee employee = employeeRepository.findById(rental.getEmployeeId()).orElseThrow(
                () -> new com.infy.exception.ResourceNotFoundException("Сотрудник с ID " + rental.getEmployeeId() + " не найден")
        );

        Rental rental1 = new Rental();
        rental1.setClient(client);
        rental1.setEmployee(employee);
        rental1.setComment(rental.getComment());
        rental1.setStatus(RentalStatus.PENDING);
        rental1.setStartDate(rental.getStartDate());
        rental1.setEndDate(rental.getEndDate());

        return rentalRepository.save(rental1);
    }


    // без статуса обновляет
    @Transactional
    public Rental update(Long id, CreateRentalRequest rental) {
        Rental existing = rentalRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Аренда с ID " + id + " не найдена"));

        existing.setStartDate(rental.getStartDate());
        existing.setEndDate(rental.getEndDate());
        existing.setComment(rental.getComment());
        existing.setClient(clientRepository.findById(rental.getClientId()).orElseThrow(
                () -> new com.infy.exception.ResourceNotFoundException("Клиент с ID " + rental.getClientId() + " не найден")
        ));
        existing.setEmployee(employeeRepository.findById(rental.getEmployeeId()).orElseThrow(
                () -> new com.infy.exception.ResourceNotFoundException("Сотрудник с ID " + rental.getEmployeeId() + " не найден")
        ));

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
