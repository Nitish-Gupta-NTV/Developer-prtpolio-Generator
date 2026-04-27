package com.example.developer.Service;

import com.example.developer.DTO.ProjectDto;
import com.example.developer.DTO.SkillDTO;
import com.example.developer.Repository.SkillRepo;
import com.example.developer.model.User;
import com.example.developer.model.projects;
import com.example.developer.model.skills;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.Repository.ProjectRepo;
import java.util.List;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ProjectServices {
    private final ProjectRepo projrepo;
    private final AuthenticatedUserlogined isuserlogin;
    private final SkillRepo skillRepo;
    @Transactional
    public ResponseEntity<?> addproject(ProjectDto projdto)
    {
        User user=isuserlogin.userlogined();
        projects pro=new projects();
        pro.setTitle(projdto.getTitle());
        pro.setDescription(projdto.getDescription());
        pro.setGithub_url(projdto.getGithub_url());
        pro.setLive_url(projdto.getLive_url());
        pro.setImage_url(projdto.getImage_url());
        pro.setFeatured(projdto.isFeatured());
        pro.setCreated_at(LocalDateTime.now());
        pro.setUser(user);
        projrepo.save(pro);
        if(projdto.getSkills()!=null&&projdto.getSkills().isEmpty())
        {
            // for each loops
            for(SkillDTO skillDTO:projdto.getSkills())
            {
                skills skillrequed=new skills();
                skillrequed.setSkills_name(skillDTO.getSkillsname());
                skillrequed.setLevels(skillDTO.getLevels());
                skillrequed.setProject(pro);
                skillRepo.save(skillrequed);
            }
        }
        return ResponseEntity.ok("project saved sucessfully");
    }
    // update exsiting project
    @Transactional
    public ResponseEntity<?>updateprojects(Long Projectid,ProjectDto prodto)
    {
        User user=isuserlogin.userlogined();
        projects pro=projrepo.findById(Projectid).orElseThrow(()->new RuntimeException("Project Not found"));
        if(!pro.getUser().getId().equals(user.getId()))
        {
            return ResponseEntity.status(403).body(" you  are not allowed to update the project");

        }
        pro.setTitle(prodto.getTitle());
        pro.setGithub_url(prodto.getGithub_url());
        pro.setDescription(prodto.getDescription());
        pro.setLive_url(prodto.getLive_url());
        pro.setImage_url(prodto.getImage_url());
        pro.setFeatured(prodto.isFeatured());
        projrepo.save(pro);
        List<skills>oldskill=skillRepo.findByProject(pro);
        System.out.println("checking the old skills"+oldskill);//checking the old skiill is it valid
        skillRepo.deleteAll(oldskill);

        if(prodto.getSkills()!=null&& !prodto.getSkills().isEmpty() )
        {
            for(SkillDTO skilldto:prodto.getSkills()){
                skills skillsrequd=new skills();
                skillsrequd.setSkills_name(skilldto.getSkillsname());
                skillsrequd.setLevels(skilldto.getLevels());
                skillsrequd.setProject(pro);
                skillRepo.save(skillsrequd);
            }
        }
        return ResponseEntity.ok("project Updated sucessfully");
    }
    @Transactional
    public ResponseEntity<?>deleteproject(Long projectid)
    {
        User user=isuserlogin.userlogined();
        projects  pro=projrepo.findById(projectid).orElseThrow(()->new RuntimeException(" project not found"));
        if(!pro.getUser().getId().equals(user.getId()))
        {
            return ResponseEntity.status(403).body("you are not allowed to delete the project");
        }
        projrepo.delete(pro);
        return ResponseEntity.ok("project deleted sucessfully");
    }
    public ResponseEntity<?>getallproject()
    {

        User user=isuserlogin.userlogined();
        List<projects>allproject=projrepo.findByUser(user);
        return ResponseEntity.ok("all projects are here");
    }
}
