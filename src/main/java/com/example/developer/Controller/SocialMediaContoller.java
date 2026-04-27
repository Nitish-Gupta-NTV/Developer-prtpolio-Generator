package com.example.developer.Controller;
import com.example.developer.DTO.SocialMediaDTO;
import com.example.developer.Service.SocialmediaServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/socialmedia")
public class SocialMediaContoller {
    private final SocialmediaServices socialmediaServices;
    @PostMapping("/add/socialmedia")
    public ResponseEntity<?> addsocialmedia( @Valid @RequestBody SocialMediaDTO socialMediaDTO)
    {
        System.out.println("data the coontroller"+socialMediaDTO);
        return socialmediaServices.Savesocialmedia(socialMediaDTO);
    }
}
