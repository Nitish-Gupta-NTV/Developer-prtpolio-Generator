package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SlugUpdateRequestDTO {
    @NotBlank(message = "Slug is required")
    @Size(min = 3, max = 40, message = "Slug must be 3-40 characters")
    @Pattern(
            regexp = "^[a-z0-9]+(-[a-z0-9]+)*$",
            message = "Slug can only contain lowercase letters, numbers, and hyphens"
    )
    private String slug;
}

