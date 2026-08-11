package com.example.developer.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class SocialMediaDTO {

    private String linkedine;
    @URL(message = "github url is not vaild")
    private String github;
    @URL(message = "coding url is not valid")
    private String codingp_platform;
}
