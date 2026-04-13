package com.infy.service;

import com.infy.dto.CreateEmployeeRequest;
import com.infy.entity.Employee;
import com.infy.entity.Rental;
import com.infy.entity.User;
import com.infy.enums.RentalStatus;
import com.infy.enums.Role;
import com.infy.exception.BadRequestException;
import com.infy.repo.EmployeeRepository;
import com.infy.repo.RentalRepository;
import com.infy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public Employee createEmployee(CreateEmployeeRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new BadRequestException("Пользователь с логином '" + request.getLogin() + "' уже существует");
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(Role.MANAGER);
        user = userRepository.save(user);

        Employee employee = new Employee();
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setDepartment(request.getDepartment());
        employee.setOfficeNumber(request.getOfficeNumber());
        employee.setWorkEmail(request.getWorkEmail());
        employee.setUser(user);

        return employeeRepository.save(employee);
    }
}
