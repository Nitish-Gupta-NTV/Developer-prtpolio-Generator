package com.example.developer.Service;

import com.example.developer.DTO.SocialMediaDTO;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.Repository.socialmediaRepo;
import com.example.developer.model.socialmedia;
@Service
@AllArgsConstructor
public class SocialmediaServices {
    private final AuthenticatedUserlogined isuserrlogined;
    private final socialmediaRepo socialrepo;
    public ResponseEntity<?>Savesocialmedia(SocialMediaDTO socialdto)
    {
        User user=isuserrlogined.userlogined();
        socialmedia social=socialrepo.findByUser(user).orElse(new socialmedia());
        social.setGithub(socialdto.getGithub());
        social.setLinkedine(socialdto.getLinkedine());
        social.setCodingp_platform(socialdto.getCodingp_platform());
        social.setUser(user);
        socialrepo.save(social);
        return ResponseEntity.ok("links are added sucessfully");

    }
}
