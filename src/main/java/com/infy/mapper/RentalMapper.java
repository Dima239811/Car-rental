package com.infy.mapper;

import com.infy.dto.RentalBriefResponse;
import com.infy.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientFullName", source = "client.user.fullName")
    @Mapping(target = "clientLogin", source = "client.user.login")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeFullName", source = "employee.user.fullName")
    @Mapping(target = "employeeLogin", source = "employee.user.login")
    RentalBriefResponse toBriefResponse(Rental rental);

    List<RentalBriefResponse> toBriefResponseList(List<Rental> rentals);
}
