package com.example.developer.Service;

import com.example.developer.Repository.ExperiencessRepo;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.DTO.experienceDTO;
import com.example.developer.model.experiences;

@Service
@AllArgsConstructor
public class ExperiencesServices {
    private final AuthenticatedUserlogined isuserlogined;
    private final ExperiencessRepo exprepo;
    //add new experience
    public ResponseEntity<?> userexperience(experienceDTO exppdto)
    {
        User user=isuserlogined.userlogined();
        experiences exp=new experiences();
        exp.setCompany(exppdto.getCompany());
        exp.setPosition(exppdto.getPosition());
        exp.setStartdate(exppdto.getStartdate());
        exp.setCurrentworking(exppdto.isCurrentworking());
        exp.setEnddate(exppdto.getEnddate());
        exp.setDescription(exppdto.getDescription());
        exp.setUser(user);
        exprepo.save(exp);
        return ResponseEntity.ok("experience add done ");
    }
    // update exting expirence
    public ResponseEntity<?>updateexperence(Long id,experienceDTO exppdto)
    {
        User user=isuserlogined.userlogined();
        experiences exp=exprepo.findById(id).orElseThrow(()->new RuntimeException("No experience found"));
        if(!exp.getUser().getId().equals(user.getId()))
        {
            return ResponseEntity.status(403).body("Not allowed");
        }
        exp.setCompany(exppdto.getCompany());
        exp.setPosition(exppdto.getPosition());
        exp.setStartdate(exppdto.getStartdate());
        exp.setCurrentworking(exppdto.isCurrentworking());
        exp.setEnddate(exppdto.getEnddate());
        exp.setDescription(exppdto.getDescription());
        exprepo.save(exp);
        return ResponseEntity.ok("experiences updated sucessfully");
    }
    // delete the experience
    public ResponseEntity<?> deleteexperience(Long id)
    {
        User user=isuserlogined.userlogined();
        experiences exp=exprepo.findById(id).orElseThrow(()->new RuntimeException(" experience not found"));
        if(!exp.getUser().getId().equals(user.getId()))
        {
            return ResponseEntity.status(403).body("Not allowed");
        }
        exprepo.delete(exp);
        return ResponseEntity.ok("Experience deleted sucessfully");

    }


}
