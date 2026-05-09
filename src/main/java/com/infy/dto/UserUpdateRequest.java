package com.infy.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String login;
    private String fullName;
    private String phone;
}
