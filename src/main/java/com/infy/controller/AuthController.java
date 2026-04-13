package com.infy.controller;

import com.infy.dto.AuthResponse;
import com.infy.dto.LoginRequest;
import com.infy.dto.RegisterClientRequest;

import com.infy.entity.Client;
import com.infy.service.AuthService;
import com.infy.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    private final ClientService clientService;

    public AuthController(AuthService authService, ClientService clientService) {
        this.authService = authService;
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<Client> register(@RequestBody RegisterClientRequest request) {
        Client client = clientService.register(request);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
