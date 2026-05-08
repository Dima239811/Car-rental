package com.infy.repo;

import com.infy.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN FETCH c.user")
    List<Client> findAllWithUser();

    Optional<Client> findByUserId(Long userId);
}
