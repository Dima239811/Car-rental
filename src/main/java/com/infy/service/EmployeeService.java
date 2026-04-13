package com.infy.service;

import com.infy.entity.Employee;
import com.infy.entity.Rental;
import com.infy.enums.RentalStatus;
import com.infy.exception.BadRequestException;
import com.infy.repo.EmployeeRepository;
import com.infy.repo.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final RentalRepository rentalRepository;


    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Employee> findAllWithUser() {
        return employeeRepository.findAllWithUser();
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Long id, Employee employee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Сотрудник с ID " + id + " не найден"));

        existing.setPosition(employee.getPosition());
        existing.setSalary(employee.getSalary());
        existing.setDepartment(employee.getDepartment());
        existing.setOfficeNumber(employee.getOfficeNumber());
        existing.setWorkEmail(employee.getWorkEmail());
        existing.setUser(employee.getUser());

        return employeeRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        List<Rental> activeRentals = rentalRepository.findByEmployeeIdAndStatusIn(id, List.of(RentalStatus.PENDING, RentalStatus.CONFIRMED, RentalStatus.ACTIVE));
        if (!activeRentals.isEmpty()) {
            throw new BadRequestException("Невозможно удалить сотрудника с ID " + id + ": есть активные аренды");
        }
        employeeRepository.deleteById(id);
    }
}
