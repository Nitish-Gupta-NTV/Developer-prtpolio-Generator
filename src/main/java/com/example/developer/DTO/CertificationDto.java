package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificationDto {
    @NotBlank(message = "title can't be blank")
    private String title;
    private String descscribe;
    @NotBlank(message = "issuer can't be blank")
    private String issuer;
    private LocalDate issued_date;
}

