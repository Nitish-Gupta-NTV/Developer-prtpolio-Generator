package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReplyRequestDTO {
    @NotBlank private String message;
}