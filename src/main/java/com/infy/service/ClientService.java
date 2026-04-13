package com.infy.service;

import com.infy.entity.Client;
import com.infy.entity.Rental;
import com.infy.enums.RentalStatus;
import com.infy.exception.BadRequestException;
import com.infy.repo.ClientRepository;
import com.infy.repo.RentalCarRepository;
import com.infy.repo.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final RentalRepository rentalRepository;

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Client> findAllWithUser() {
        return clientRepository.findAllWithUser();
    }

    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, Client client) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Клиент с ID " + id + " не найден"));

        existing.setDriverLicense(client.getDriverLicense());
        existing.setBirthDate(client.getBirthDate());
        existing.setRentCount(client.getRentCount());
        existing.setPersonalEmail(client.getPersonalEmail());
        existing.setUser(client.getUser());

        return clientRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        List<Rental> activeRentals = rentalRepository.findByClientIdAndStatusIn(id, List.of(RentalStatus.PENDING, RentalStatus.CONFIRMED, RentalStatus.ACTIVE));
        if (!activeRentals.isEmpty()) {
            throw new BadRequestException("Невозможно удалить клиента с ID " + id + ": есть активные аренды");
        }
        clientRepository.deleteById(id);
    }
}
