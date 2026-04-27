package com.example.developer.Controller;
import com.example.developer.DTO.EducationDto;
import com.example.developer.Service.EduactionServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/developer/educations")
public class EducationsController {
    private final EduactionServices eduactionServices;
    @PostMapping("/addeducation")
    public ResponseEntity<?> addeducation(@Valid @RequestBody EducationDto educationDto)
    {
        return eduactionServices.saveeducation(educationDto);
    }
}
