package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    private String educationlevel;
    private String institution;
    private String grade;
    private Integer passingYear;
    private boolean ongoing;
    private String  educationame;
    //private String ifanythinkkselse;
}
