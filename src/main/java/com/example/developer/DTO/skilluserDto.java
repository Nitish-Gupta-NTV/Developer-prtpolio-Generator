package com.example.developer.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class skilluserDto {
    @NotBlank(message="skill name is requried")
    private String  skills;

}
