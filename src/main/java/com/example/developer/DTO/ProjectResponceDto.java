package com.example.developer.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectResponceDto {
    private Long id;
    private String title;
    private String description;
    private String githubUrl;
    private String liveUrl;
    private String imageUrl;
    private boolean featured;
    private List<SkillDTO>skilldto;

}
