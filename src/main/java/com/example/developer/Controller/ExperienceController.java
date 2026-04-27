package com.example.developer.Controller;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.developer.Service.ExperiencesServices;
import com.example.developer.DTO.experienceDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/experience")
public class ExperienceController {
    private final ExperiencesServices experiencesServices;
    @PostMapping("/add/experiences")
    public ResponseEntity<?>addexperiences(@Valid @RequestBody experienceDTO dto)
    {
        System.out.println("data the coontroller"+dto);
        return experiencesServices.userexperience(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>addexperiences(@PathVariable Long id,@Valid @RequestBody experienceDTO dto){
        return experiencesServices.updateexperence(id,dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteexperiences(@Valid @PathVariable Long id)
    {
        return experiencesServices.deleteexperience(id);
    }
}
