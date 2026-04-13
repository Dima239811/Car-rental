package com.infy.controller;

import com.infy.dto.CreateEmployeeRequest;
import com.infy.dto.EmployeeBriefResponse;
import com.infy.entity.Employee;
import com.infy.exception.ResourceNotFoundException;
import com.infy.mapper.EmployeeMapper;
import com.infy.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<EmployeeBriefResponse>> getAll() {
        return ResponseEntity.ok(employeeMapper.toBriefResponseList(employeeService.findAllWithUser()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<EmployeeBriefResponse> getById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(employeeMapper::toBriefResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + id + " не найден"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeBriefResponse> create(@RequestBody CreateEmployeeRequest request) {
        Employee created = employeeService.createEmployee(request);
        return ResponseEntity.ok(employeeMapper.toBriefResponse(created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeBriefResponse> update(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.update(id, employee);
        return ResponseEntity.ok(employeeMapper.toBriefResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
