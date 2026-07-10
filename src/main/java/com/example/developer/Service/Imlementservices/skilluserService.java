package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.skilluserDto;
import com.example.developer.DTO.skilluserWraperDTO;
import com.example.developer.Repository.skilluserRepo;
import com.example.developer.model.User;
import com.example.developer.model.skilluser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class skilluserService {
    private final skilluserRepo skillrepo;
    private final AuthenticatedUserlogined isuserlogined;

   public ResponseEntity<?> adduserskill (skilluserWraperDTO skillwraperdto)
    {System.out.println("enter the for the user skill to save in the services layer");
        User user=isuserlogined.userlogined();
        if(skillwraperdto==null||skillwraperdto.getSkills() == null ||skillwraperdto.getSkills().isEmpty())
        {
            return ResponseEntity.badRequest().body("skillwraper is empty or the no the null");
        }
       for(skilluserDto userdto: skillwraperdto.getSkills())
       {
           skilluser skillentity=new skilluser();
           skillentity.setSkill_name(userdto.getSkills());
           skillentity.setUser(user);
           skillrepo.save(skillentity);
       }

        return ResponseEntity.ok("skill saved sucessfully");
    }


}
