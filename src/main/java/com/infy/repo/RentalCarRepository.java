package com.infy.repo;

import com.infy.entity.CarRentalId;
import com.infy.entity.Rental;
import com.infy.entity.RentalCar;
import com.infy.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalCarRepository extends JpaRepository<RentalCar, CarRentalId> {
    @Query("SELECT rc FROM RentalCar rc JOIN FETCH rc.car JOIN FETCH rc.rental")
    List<RentalCar> findAllWithCarAndRental();

    @Query("SELECT rc FROM RentalCar rc JOIN FETCH rc.car JOIN FETCH rc.rental WHERE rc.id.rentalId = :rentalId")
    List<RentalCar> findByRentalIdWithCarAndRental(@Param("rentalId") Long rentalId);

    List<RentalCar> findByCarIdAndRentalStatusIn(Long carId, List<RentalStatus> statuses);
    List<RentalCar> findByRentalIdAndRentalStatusIn(Long rentalId, List<RentalStatus> statuses);

    @Query("""
        SELECT rc FROM RentalCar rc 
        WHERE rc.car.id = :carId 
        AND rc.rental.startDate < :endDate 
        AND rc.rental.endDate > :startDate
    """)
    List<RentalCar> findConflictingRentals(
            @Param("carId") Long carId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
