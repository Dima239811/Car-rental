package com.infy.repo;

import com.infy.entity.Rental;
import com.infy.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r JOIN FETCH r.client JOIN FETCH r.employee")
    List<Rental> findAllWithClientAndEmployee();

    @Query("SELECT r FROM Rental r JOIN FETCH r.client JOIN FETCH r.employee LEFT JOIN FETCH r.rentalCars rc LEFT JOIN FETCH rc.car WHERE r.id = :id")
    List<Rental> findByIdWithDetails(@Param("id") Long id);

    List<Rental> findByClientIdAndStatusIn(Long clientId, List<RentalStatus> statuses);
    List<Rental> findByEmployeeIdAndStatusIn(Long employeeId, List<RentalStatus> statuses);

}
