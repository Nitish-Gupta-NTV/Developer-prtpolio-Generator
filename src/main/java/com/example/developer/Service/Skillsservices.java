package com.example.developer.Service;

import com.example.developer.Repository.SkillRepo;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com .example.developer.DTO.SkillDTO;
import com.example.developer.model.skills;
import com.example.developer.Repository.ProjectRepo;


@Service
@AllArgsConstructor
public class Skillsservices {
    private final SkillRepo skillRepo;
    private final AuthenticatedUserlogined isuserlogined;
    private final ProjectRepo projectRepo;
    public ResponseEntity<?> addskills(SkillDTO dto)
    {
        System.out.println("eneter the skill layer from the controller ");
        User user=isuserlogined.userlogined();
        skills skillsentity=new skills();
        skillsentity.setSkills_name(dto.getSkillsname());
        skillsentity.setLevels(dto.getLevels());
        //skillsentity.setProject(projectRepo.findByUser(user));
        skillRepo.save(skillsentity);
        return ResponseEntity.ok("skills save");
    }
}
