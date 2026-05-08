package com.infy.dto;

import com.infy.entity.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private Long userId;
    private String login;
    private String role;

    public AuthResponse(String token, Long userId, String login, String role) {
        this.token = token;
        this.userId = userId;
        this.login = login;
        this.role = role;
    }
}
