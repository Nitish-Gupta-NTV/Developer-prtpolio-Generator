package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticatedResponce {
    private String accessToken;
    private String refreshToken;
    private String role;
    private String email;
}
