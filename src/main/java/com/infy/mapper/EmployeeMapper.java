package com.infy.mapper;

import com.infy.dto.EmployeeBriefResponse;
import com.infy.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "login", source = "user.login")
    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "phone", source = "user.phone")
    EmployeeBriefResponse toBriefResponse(Employee employee);

    List<EmployeeBriefResponse> toBriefResponseList(List<Employee> employees);
}
