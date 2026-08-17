package com.example.developer.Service.ResponceGenearatorServices;

import com.example.developer.DTO.*;
import com.example.developer.GlobalExceptionHandler.PortfolioNotPublishedException;
import com.example.developer.Service.Imlementservices.AuthenticatedUserlogined;
import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import com.example.developer.model.projects;
import com.example.developer.model.skills;
import com.example.developer.model.experiences;
import com.example.developer.model.certaficatios;
import com.example.developer.model.skilluser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.Repository.PortfolioRepo;
import com.example.developer.Repository.CertaficationsRepo;
import com.example.developer.Repository.EducationRepo;
import com.example.developer.Repository.ExperiencessRepo;
import com.example.developer.Repository.ProjectRepo;
import com.example.developer.Repository.SkillRepo;
import com.example.developer.Repository.socialmediaRepo;
import com.example.developer.Repository.skilluserRepo;
import com.example.developer.GlobalExceptionHandler.PortfolioNotFoundException;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PotopolioGeneratorservice {

    private AuthenticatedUserlogined isuserlogined;
    private final  PortfolioRepo portrepo;
    private final ProjectRepo prorjepo;
    private final SkillRepo skillrepo;
    private final ExperiencessRepo exprepo;
    private final EducationRepo edurepo;
    private final CertaficationsRepo certirepo;
    private final socialmediaRepo socirepo;
    private final skilluserRepo skilluserrepo;

/*
    // fetch all thedetails send to the frontend for generating frontend part
    public ResponseEntity<?> generateportfolio ()
    {
        User user=isuserlogined.userlogined(); // for checking whether user is logined or not
        // check whether the protfolio exits or not
        Portfolio prot=portrepo.findByUser(user).orElseThrow(()->new RuntimeException(" portfolio does not exits"));
        PortfolioResponceDto responce=new PortfolioResponceDto();
        // basic info
        responce.setEmail(user.getEmail());
        responce.setName(user.getName());
        responce.setPhonenumber(user.getPhonenumber());
        // portfolio info
        responce.setHeadline(prot.getHeadline());
        responce.setBio(prot.getBio());
        responce.setAbout(prot.getAbout());
        responce.setLocation(prot.getLocation());
       responce.setProfileImage(prot.getProfileimage());
        responce.setThemeId(prot.getTheme_id());
        responce.setPublished(prot.is_published());
        // responce.setThemeId(prot.getTheme_id()); // it maay due to the relation is not going to build
       responce.setPublished(prot.is_published());
       responce.setSlug(prot.getSlug());

       // now project section turns

        List<projects> proj=prorjepo.findByUser(user);
        List<ProjectResponceDto> projdto=new ArrayList<>();
        // using the for each loop  to enter the project details
        for(projects  projentity:proj) {
            ProjectResponceDto projectResponceDto = new ProjectResponceDto();
            projectResponceDto.setId(projentity.getId());
            projectResponceDto.setTitle(projentity.getTitle());
            projectResponceDto.setDescription(projentity.getDescription());
            projectResponceDto.setGithubUrl(projentity.getGithub_url());
            projectResponceDto.setLiveUrl(projentity.getLive_url());
            projectResponceDto.setImageUrl(projentity.getImage_url());
            projectResponceDto.setFeatured(projentity.isFeatured());

            // adding the skill in the project section
            List<skills> skillsList = skillrepo.findByProject(projentity);
            List<SkillDTO>skilldto=new ArrayList<>();
            for (skills skillentity:skillsList)
            {
                SkillDTO skilldtofor=new SkillDTO();
                skilldtofor.setSkillsname(skillentity.getSkills_name());
                skilldtofor.setLevels(skillentity.getLevels());
                skilldto.add(skilldtofor);
            }
            projectResponceDto.setSkilldto(skilldto);
            projdto.add(projectResponceDto);
        }
        responce.setProresponce(projdto);


        // now adding the experiences
        List<experiences> experentity=exprepo.findByUser(user);
        List<experienceDTO> expdto=new ArrayList<>();
        for (experiences exp:experentity)
        {
            experienceDTO exptdtofor=new experienceDTO();
            exptdtofor.setCompany(exp.getCompany());
            exptdtofor.setPosition(exp.getPosition());
            exptdtofor.setStartdate(exp.getStartdate());
            exptdtofor.setEnddate(exp.getEnddate());
            exptdtofor.setCurrentworking(exp.isCurrentworking());
            exptdtofor.setDescription(exp.getDescription());
            expdto.add(exptdtofor);
        }
        responce.setExperienceDTOS(expdto);


        // now adding the education details

        edurepo.findByUser(user).ifPresent(educations -> {
            EducationDto edudto=new EducationDto();
            edudto.setInstitution(educations.getInstitution());
            edudto.setEducationlevel(educations.getEducationlevel());
            edudto.setGrade(educations.getGrade());
            edudto.setPassingYear(educations.getPassingYear());
            edudto.setOngoing(educations.isOngoing());
            edudto.setEducationame(educations.getEducationame());
            responce.setEducationdto(edudto);

        });

        //Now adding the Certifications

        List<certaficatios>certaficatiosList=certirepo.findByUser(user);
        List<CertificationDto>certdto=new ArrayList<>();
        for(certaficatios certentiy:certaficatiosList)
        {
            CertificationDto certificationDto=new CertificationDto();
            certificationDto.setTitle(certentiy.getTitle());
            certificationDto.setDescscribe(certentiy.getDescscribe());
            certificationDto.setDescscribe(certentiy.getDescscribe());
            certificationDto.setIssued_date(certentiy.getIssued_date());
            certdto.add(certificationDto);
        }
        responce.setCertidto(certdto); //adding certifications

        //Now adding the Socialmedia Responce data from the database
        socirepo.findByUser(user).ifPresent(socialmedia -> {
            SocialMediaDTO socialdto=new SocialMediaDTO();
            socialdto.setGithub(socialmedia.getGithub());
            socialdto.setLinkedine(socialmedia.getLinkedine());
            socialdto.setCodingp_platform(socialmedia.getCodingp_platform());
            responce.setSocialdto(socialdto);

        });

        //List<skilluser>skilluserdtoslist=skilluserrepo.findByUser(user);





     return ResponseEntity.ok(responce);
    }
    public ResponseEntity<?> getPublicPortfolio(String slug){
        Portfolio prot=portrepo.findBySlug(slug).orElseThrow(()-> new PortfolioNotFoundException("prtoplio not found exception") );
        if(!prot.is_published())
        {
            throw new PortfolioNotPublishedException("prtopolio not published ");

        }
        return generateportfolio();
    }*/
// fetch all the details, send to the frontend for generating frontend part
public ResponseEntity<?> generateportfolio() {
    User user = isuserlogined.userlogined();
    Portfolio prot = portrepo.findByUser(user).orElseThrow(() -> new RuntimeException("portfolio does not exist"));
    return ResponseEntity.ok(buildPortfolioResponse(user, prot));
}
   @Transactional
    public ResponseEntity<?> getPublicPortfolio(String slug) {
        Portfolio prot = portrepo.findBySlug(slug)
                .orElseThrow(() -> new PortfolioNotFoundException("portfolio not found"));

        if (!prot.is_published()) { // fixed: was missing the "!"
            throw new PortfolioNotPublishedException("portfolio not published");
        }
        portrepo.incrementViewCount(slug);
        User user = prot.getUser(); // pull the owner off the portfolio itself, not the logged-in session
        return ResponseEntity.ok(buildPortfolioResponse(user, prot));
    }
    // in PotopolioGeneratorservice
    public ResponseEntity<?> getAnalytics() {
        User user = isuserlogined.userlogined();
        Portfolio prot = portrepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("portfolio does not exist"));

        return ResponseEntity.ok(new AnalyticsDTO(prot.getViewCount(), prot.getLastViewedAt(), prot.getSlug()));
    }

    // shared builder — everything below is your existing logic, unchanged, just
// extracted into a method that takes user/prot as arguments instead of
// re-fetching them from the session every time
    private PortfolioResponceDto buildPortfolioResponse(User user, Portfolio prot) {
        PortfolioResponceDto responce = new PortfolioResponceDto();

        responce.setEmail(user.getEmail());
        responce.setName(user.getName());
        responce.setPhonenumber(user.getPhonenumber());

        responce.setHeadline(prot.getHeadline());
        responce.setBio(prot.getBio());
        responce.setAbout(prot.getAbout());
        responce.setLocation(prot.getLocation());
        responce.setProfileImage(prot.getProfileimage());
        responce.setThemeId(prot.getTheme_id());
        responce.setPublished(prot.is_published());
        responce.setSlug(prot.getSlug());

        List<projects> proj = prorjepo.findByUser(user);
        List<ProjectResponceDto> projdto = new ArrayList<>();
        for (projects projentity : proj) {
            ProjectResponceDto projectResponceDto = new ProjectResponceDto();
            projectResponceDto.setId(projentity.getId());
            projectResponceDto.setTitle(projentity.getTitle());
            projectResponceDto.setDescription(projentity.getDescription());
            projectResponceDto.setGithubUrl(projentity.getGithub_url());
            projectResponceDto.setLiveUrl(projentity.getLive_url());
            projectResponceDto.setImageUrl(projentity.getImage_url());
            projectResponceDto.setFeatured(projentity.isFeatured());

            List<skills> skillsList = skillrepo.findByProject(projentity);
            List<SkillDTO> skilldto = new ArrayList<>();
            for (skills skillentity : skillsList) {
                SkillDTO skilldtofor = new SkillDTO();
                skilldtofor.setSkillsname(skillentity.getSkills_name());
                skilldtofor.setLevels(skillentity.getLevels());
                skilldto.add(skilldtofor);
            }
            projectResponceDto.setSkilldto(skilldto);
            projdto.add(projectResponceDto);
        }
        responce.setProresponce(projdto);

        List<experiences> experentity = exprepo.findByUser(user);
        List<experienceDTO> expdto = new ArrayList<>();
        for (experiences exp : experentity) {
            experienceDTO exptdtofor = new experienceDTO();
            exptdtofor.setCompany(exp.getCompany());
            exptdtofor.setPosition(exp.getPosition());
            exptdtofor.setStartdate(exp.getStartdate());
            exptdtofor.setEnddate(exp.getEnddate());
            exptdtofor.setCurrentworking(exp.isCurrentworking());
            exptdtofor.setDescription(exp.getDescription());
            expdto.add(exptdtofor);
        }
        responce.setExperienceDTOS(expdto);
        //normal skill of the user
        List<skilluser> skillsuerentity=skilluserrepo.findByUser(user);
        List<skilluserDto> dto=new ArrayList<>();
        for(skilluser skill:skillsuerentity)
        {
            skilluserDto skilldto=new skilluserDto();
            skilldto.setSkills(skill.getSkill_name());
            dto.add(skilldto);

        }
        responce.setUserskilldto(dto);
//end of the normal skill of the user
        edurepo.findByUser(user).ifPresent(educations -> {
            EducationDto edudto = new EducationDto();
            edudto.setInstitution(educations.getInstitution());
            edudto.setEducationlevel(educations.getEducationlevel());
            edudto.setGrade(educations.getGrade());
            edudto.setPassingYear(educations.getPassingYear());
            edudto.setOngoing(educations.isOngoing());
            edudto.setEducationame(educations.getEducationame());
            responce.setEducationdto(edudto);
        });

        List<certaficatios> certaficatiosList = certirepo.findByUser(user);
        List<CertificationDto> certdto = new ArrayList<>();
        for (certaficatios certentiy : certaficatiosList) {
            CertificationDto certificationDto = new CertificationDto();
            certificationDto.setTitle(certentiy.getTitle());
            certificationDto.setDescscribe(certentiy.getDescscribe());
            certificationDto.setIssued_date(certentiy.getIssued_date());
            certdto.add(certificationDto);
        }
        responce.setCertidto(certdto);

        socirepo.findByUser(user).ifPresent(socialmedia -> {
            SocialMediaDTO socialdto = new SocialMediaDTO();
            socialdto.setGithub(socialmedia.getGithub());
            socialdto.setLinkedine(socialmedia.getLinkedine());
            socialdto.setCodingp_platform(socialmedia.getCodingp_platform());
            responce.setSocialdto(socialdto);
        });

        return responce;
    }

}
