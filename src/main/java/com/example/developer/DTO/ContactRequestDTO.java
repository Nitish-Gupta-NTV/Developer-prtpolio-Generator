package com.example.developer.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequestDTO {
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
