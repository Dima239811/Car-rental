package com.infy.mapper;

import com.infy.dto.RentalBriefResponse;
import com.infy.dto.RentalCarBriefResponse;
import com.infy.entity.Rental;
import com.infy.entity.RentalCar;
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
    @Mapping(target = "cars", source = "rentalCars")
    RentalBriefResponse toBriefResponse(Rental rental);

    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "brand", source = "car.brand")
    @Mapping(target = "model", source = "car.model")
    @Mapping(target = "price", source = "car.price")
    @Mapping(target = "deposit", source = "car.deposit")
    RentalCarBriefResponse toRentalCarBriefResponse(RentalCar rentalCar);

    default List<RentalCarBriefResponse> mapRentalCarsToBriefResponses(List<RentalCar> rentalCars) {
        if (rentalCars == null) {
            return List.of();
        }
        return rentalCars.stream()
                .map(this::toRentalCarBriefResponse)
                .toList();
    }

    List<RentalBriefResponse> toBriefResponseList(List<Rental> rentals);
}
