package com.example.developer.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Register {
    private String user_name;
    private String password;
    private String role;
    private String name;
    private Long phone_number;
    private String email;
}
