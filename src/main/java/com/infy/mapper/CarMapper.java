package com.infy.mapper;

import com.infy.dto.CarCreateUpdateDTO;
import com.infy.entity.Car;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarCreateUpdateDTO toCarCreateUpdateDTO(Car car);

    List<CarCreateUpdateDTO> toCarCreateUpdateDTOList(List<Car> carList);
}
