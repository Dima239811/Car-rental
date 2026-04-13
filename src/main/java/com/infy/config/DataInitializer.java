package com.infy.config;

import com.infy.entity.Employee;
import com.infy.entity.User;
import com.infy.enums.Role;
import com.infy.repo.EmployeeRepository;
import com.infy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByLogin("admin").isEmpty()) {
            User admin = new User();
            admin.setLogin("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setPhone("+79001234567");
            admin.setRole(Role.ADMIN);
            admin = userRepository.save(admin);

            Employee emp = new Employee();
            emp.setPosition("Administrator");
            emp.setSalary(0.0);
            emp.setDepartment("IT");
            emp.setOfficeNumber("001");
            emp.setWorkEmail("admin@company.com");
            emp.setUser(admin);
            employeeRepository.save(emp);
        }
    }
}
