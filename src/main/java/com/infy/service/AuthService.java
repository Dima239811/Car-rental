package com.infy.service;

import com.infy.entity.User;
import com.infy.exception.BadRequestException;
import com.infy.exception.ResourceNotFoundException;
import com.infy.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User login(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        return user;
    }

    @Transactional
    public User register(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new BadRequestException("Login already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
