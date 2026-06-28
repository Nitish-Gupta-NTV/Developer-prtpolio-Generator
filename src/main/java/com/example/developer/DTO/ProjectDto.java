package com.example.developer.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class ProjectDto {
    @NotBlank(message = "Project Title is Requried")
    private  String title;
    private  String  description;
    private String github_url;
    private String   live_url;
    private String  image_url;
    private  boolean featured;
    private LocalDateTime created_at;
    @NotEmpty(message = "Project must have at least one skill")
    @Valid
    private List<SkillDTO> skills;
}
