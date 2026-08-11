package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.EducationDto;
import com.example.developer.Repository.EducationRepo;
import com.example.developer.model.educations;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class EduactionServices {
    private final AuthenticatedUserlogined isuserlogined;
    private final EducationRepo edurepo;
    public ResponseEntity<?> saveeducation(EducationDto edudto)
    {
        User user=isuserlogined.userlogined();
        educations edu=edurepo.findByUser(user).orElse(new educations());
        if(!edudto.isOngoing()&&edudto.getPassingYear()==null)
        {
            return ResponseEntity.badRequest().body("passing year is requried when education is completed ");

        }
        edu.setEducationlevel(edudto.getEducationlevel());
        edu.setInstitution(edudto.getInstitution());
        edu.setGrade(edudto.getGrade());
        edu.setEducationame(edudto.getEducationame());
        //edu.setPassingYear(edudto.getPassingYear());
        //edu.setOngoing(edudto.get());
        edu.setOngoing(edudto.isOngoing());
        edu.setUser(user);
        if(edudto.isOngoing())
        {
            edu.setPassingYear(null);

        }
        else {
            edu.setPassingYear(edudto.getPassingYear());
        }
        edurepo.save(edu);
        return ResponseEntity.ok("education is added sucessfully ");
    }

}
