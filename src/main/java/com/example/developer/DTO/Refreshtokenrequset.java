package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Refreshtokenrequset {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
