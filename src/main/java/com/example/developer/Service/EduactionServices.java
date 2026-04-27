package com.example.developer.Service;

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
        edu.setHigh_schooling(edudto.getHigh_schooling());
        edu.setSecondary_schooling(edudto.getSecondary_schooling());
        edu.setBachelor(edudto.getBachelor());
        edu.setPostgraducation(edudto.getPostgraducation());
        edu.setUser(user);
        edurepo.save(edu);
        return ResponseEntity.ok("education is added sucessfully ");
    }

}
