package com.infy.controller;

import com.infy.dto.ClientBriefResponse;
import com.infy.dto.RequestClient;
import com.infy.entity.Client;
import com.infy.exception.ResourceNotFoundException;
import com.infy.mapper.ClientMapper;
import com.infy.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<ClientBriefResponse>> getAll() {
        List<ClientBriefResponse> clients = clientService.findAllWithUser();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ClientBriefResponse> getById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(clientMapper::toBriefResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с ID " + id + " не найден"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Client> create(@RequestBody RequestClient client) {
        return ResponseEntity.ok(clientService.create(client.getUserId(), client));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ClientBriefResponse> update(@PathVariable Long id, @RequestBody RequestClient client) {
        Client updated = clientService.update(id, client);
        return ResponseEntity.ok(clientMapper.toBriefResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
