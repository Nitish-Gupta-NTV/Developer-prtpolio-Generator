package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @NotBlank(message = "username can't be empty")
    private String user_name;
    @NotBlank(message = "password is requried")
    private String password;
    @NotBlank(message = "Email can't be balnk")
    private String email;
}
