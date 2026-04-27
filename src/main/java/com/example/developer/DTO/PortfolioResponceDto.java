package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PortfolioResponceDto {
    private String name;
    private String email;
    private Long phonenumber;
    private String title;
    private String bio;
    private String about;
    private String link;
    private String profileImage;
    private Long themeId;
    private boolean isPublished;
    private List<ProjectResponceDto>proresponce;
    private List<experienceDTO>experienceDTOS;
    private EducationDto educationdto;
    private List<CertificationDto>certidto;
    private SocialMediaDTO socialdto;

}
