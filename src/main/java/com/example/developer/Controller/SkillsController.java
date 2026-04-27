package com.example.developer.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.developer.DTO.SkillDTO;
import com.example.developer.Service.Skillsservices;

@RestController
@RequestMapping("/add/developer/skills")
@AllArgsConstructor
public class SkillsController {
    private final Skillsservices skillsservices;
    @PostMapping("/skills/save")
    public ResponseEntity<?>saveskils(@Valid @RequestBody SkillDTO skillDTO)
    {
        System.out.println("data from the skills sections "+skillDTO);
        return skillsservices.addskills(skillDTO);
    }
}
