package com.example.developer.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class skilluserWraperDTO {
    @Valid
    @NotEmpty(message = "At least one skill is required")
    private List<skilluserDto> skills;

}
