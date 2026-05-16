package com.infy.repo;

import com.infy.entity.AnalyticsProjection;
import com.infy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<User, Long> {
    // данные из таблицы rentals

    //@Query("SELECT r FROM Rental r") (JPQL)
    @Query(value = """
    SELECT r.id, r.status, r.start_date, r.end_date, r.comment,
           c.id, u.full_name, c.driver_license,
           e.id, e.position
    FROM rentals r
    JOIN clients c ON r.client_id = c.id
    JOIN users u ON c.user_id = u.id
    JOIN employees e ON r.employee_id = e.id
    ORDER BY r.start_date DESC
    """, nativeQuery = true)
    List<Object[]> getRentalsReport();


    // SQL с параметром brand
    @Query(value = """
            SELECT
              c.id,
              c.driver_license,
              u.full_name,

              COUNT(DISTINCT r.id) as total_rentals,

              COUNT(DISTINCT CASE
                  WHEN car.brand = :brand THEN r.id
              END) as brand_rentals

          FROM clients c
          JOIN users u ON c.user_id = u.id
          LEFT JOIN rentals r ON c.id = r.client_id
          LEFT JOIN rental_car rc ON r.id = rc.rental_id
          LEFT JOIN cars car ON rc.car_id = car.id

          GROUP BY c.id, c.driver_license, u.full_name
          ORDER BY total_rentals DESC
        """, nativeQuery = true)
    List<Object[]> getClientsByCarBrand(@Param("brand") String brand);

    // SQL с параметром minRentals (DISTINCT + HAVING + подзапрос)
    @Query(value = """
            SELECT c.id, c.driver_license, u.full_name,
                   COUNT(DISTINCT r.id) as total_rentals,
                   COUNT(DISTINCT car.id) as unique_brands
            FROM clients c
            JOIN users u ON c.user_id = u.id
            JOIN rentals r ON c.id = r.client_id
            JOIN rental_car rc ON r.id = rc.rental_id
            JOIN cars car ON rc.car_id = car.id
            GROUP BY c.id, c.driver_license, u.full_name
            HAVING COUNT(DISTINCT r.id) >= :minRentals
            ORDER BY total_rentals DESC
        """, nativeQuery = true)
    List<Object[]> getTopClientsByRentals(@Param("minRentals") int minRentals);

    // SQL без параметра (данные из cars)
    @Query(value = """
        SELECT
            car.brand,
            COUNT(DISTINCT rc.rental_id) as rental_times,
            COALESCE(AVG(rc.discount), 0) as avg_discount
        FROM cars car
        LEFT JOIN rental_car rc ON car.id = rc.car_id
        GROUP BY car.brand
        ORDER BY rental_times DESC""", nativeQuery = true)
    List<Object[]> getCarsRentalReport();


    @Query(value = """
    SELECT
      COUNT(DISTINCT r.id) as totalRentals,
      COUNT(DISTINCT r.client_id) as totalClients,
    
      COALESCE(SUM(
          (SELECT SUM(car.price - COALESCE(rc2.discount, 0))
           FROM rental_car rc2
           JOIN cars car ON rc2.car_id = car.id
           WHERE rc2.rental_id = r.id)
      ), 0) as totalIncome,
    
      (
          SELECT u.full_name
          FROM clients c
          JOIN users u ON c.user_id = u.id
          JOIN rentals r2 ON c.id = r2.client_id
          WHERE r2.start_date BETWEEN :startDate AND :endDate
          GROUP BY c.id, u.full_name
          ORDER BY COUNT(r2.id) DESC
          LIMIT 1
      ) as topClient
    
    FROM rentals r
    WHERE r.start_date BETWEEN :startDate AND :endDate
""", nativeQuery = true)
    AnalyticsProjection getAnalyticsByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
