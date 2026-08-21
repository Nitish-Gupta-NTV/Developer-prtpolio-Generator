package com.example.developer.DTO;

import lombok.Data;

@Data
public class RegisterRequest {

    private String user_name;
    private String password;
    private String role;
    private String name;
    private Long phone_number;
    private String email;
}
