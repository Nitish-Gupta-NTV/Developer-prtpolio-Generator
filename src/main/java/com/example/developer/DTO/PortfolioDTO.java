package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PortfolioDTO {
    @NotBlank(message = "Title is requried")
    private String Title;
    private String link;
    private String bio;
    private String about;
    private String profileimage;
    private Long theme_id;
   // private boolean is_published;
    private LocalDate created_time;
    private LocalDate update_time;
}
