package com.infy.controller;

import com.infy.dto.LoginRequest;
import com.infy.entity.User;
import com.infy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getLogin(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody LoginRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        User saved = authService.register(user);
        return ResponseEntity.ok(saved);
    }
}
