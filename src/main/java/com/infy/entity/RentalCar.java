package com.infy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalCar {
    @EmbeddedId
    private CarRentalId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carId")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rentalId")
    private Rental rental;

    private Double discount;
}
