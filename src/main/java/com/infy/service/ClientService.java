package com.infy.service;

import com.infy.entity.Client;
import com.infy.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

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
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
