package com.infy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Integer year;

    @Column(name = "price")
    private Double price;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "available")
    private Boolean available;

    // Данные техпаспорта
    @Column(name = "vin")
    private String vin;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "engine_volume")
    private BigDecimal engineVolume;

    @Column(name = "color")
    private String color;

    @Column(name = "insurance_valid_until")
    private LocalDate insuranceValidUntil; // Страховка до

    @Column(name = "inspection_valid_until")
    private LocalDate inspectionValidUntil;


    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<RentalCar> rentalCars;
}
