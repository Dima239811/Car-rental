package com.infy.service;


import com.infy.dto.AuthResponse;
import com.infy.dto.LoginRequest;
import com.infy.dto.RegisterClientRequest;
import com.infy.entity.User;
import com.infy.enums.Role;
import com.infy.repo.UserRepository;
import com.infy.utils.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }


    public AuthResponse login(LoginRequest request) {
        var userDetails = customUserDetailsService.loadUserByUsername(request.getLogin());

        if (userDetails == null || !checkPassword(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid login or password");
        }

        User user = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();


        String token = jwtUtils.generateJwtToken(userDetails);
        return new AuthResponse(token, user.getId(), user.getLogin(), user.getRole().name());
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
