package com.infy.service;

import com.infy.dto.ClientBriefResponse;
import com.infy.dto.ClientProfileResponse;
import com.infy.dto.RegisterClientRequest;
import com.infy.dto.RequestClient;
import com.infy.entity.Client;
import com.infy.entity.Rental;
import com.infy.entity.User;
import com.infy.enums.RentalStatus;
import com.infy.enums.Role;
import com.infy.exception.BadRequestException;
import com.infy.exception.ResourceNotFoundException;
import com.infy.mapper.ClientMapper;
import com.infy.repo.ClientRepository;
import com.infy.repo.RentalCarRepository;
import com.infy.repo.RentalRepository;
import com.infy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;

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
    public Client create(Long userId, RequestClient request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Пользователь с ID " + userId + " не найден"));

        Client client = clientMapper.toEntity(request);
        client.setUser(user);

        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, RequestClient request) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new com.infy.exception.ResourceNotFoundException("Клиент с ID " + id + " не найден"));

        Client updated = clientMapper.toEntity(request);
        existing.setDriverLicense(updated.getDriverLicense());
        existing.setBirthDate(updated.getBirthDate());
        existing.setPersonalEmail(updated.getPersonalEmail());

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

    @Transactional
    public void deleteById(String login) {
        Client client = clientRepository.findByUser_Login(login).orElse(null);
        if (client == null) {
            throw new ResourceNotFoundException(String.format("Клиент с login %s не найден", login));
        }

        List<Rental> activeRentals = rentalRepository.findByClientIdAndStatusIn(client.getId(), List.of(RentalStatus.PENDING, RentalStatus.CONFIRMED, RentalStatus.ACTIVE));
        if (!activeRentals.isEmpty()) {
            throw new BadRequestException("Невозможно удалить клиента с Login " + login + ": есть активные аренды");
        }
        clientRepository.deleteById(client.getId());
    }

    @Transactional
    public Client register(RegisterClientRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new BadRequestException("Пользователь с логином '" + request.getLogin() + "' уже существует");
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(Role.CLIENT);
        user = userRepository.save(user);

        Client client = clientMapper.toEntity(request);
        client.setUser(user);

        return clientRepository.save(client);
    }

    public Client findByUserId(Long id) {
        return clientRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с id " + id + " не найден"));
    }

    @Transactional
    public Client updateByUserId(Long userId, RequestClient request) {

        Client existing = clientRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Клиент с userId " + userId + " не найден"));

        existing.setDriverLicense(request.getDriverLicense());
        existing.setBirthDate(request.getBirthDate());
        existing.setPersonalEmail(request.getPersonalEmail());
        existing.setRentCount(request.getRentCount());

        return clientRepository.save(existing);
    }

    @Transactional
    public Client updateByLogin(String login, RequestClient request) {
        Client existing = clientRepository.findByUser_Login(login)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Клиент с login " + login + " не найден"
                        ));

        existing.setDriverLicense(request.getDriverLicense());
        existing.setBirthDate(request.getBirthDate());
        existing.setPersonalEmail(request.getPersonalEmail());

        return clientRepository.save(existing);
    }
}

