package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class experienceDTO {
    @NotBlank(message = "Company name can't be blank if you don't have then add the college name ")
    private String company;
    private LocalDate startdate;
    private LocalDate enddate;
    private String position;
    private boolean currentworking;
    private String description;
   // private boolean isCurrent;

}
