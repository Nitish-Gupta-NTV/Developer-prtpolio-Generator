package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SkillDTO {
    @NotBlank(message = "Skill name is requried")
    private String skillsname;
    private String levels;

}
