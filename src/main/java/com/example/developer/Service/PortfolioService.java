package com.example.developer.Service;

import com.example.developer.DTO.PortfolioDTO;
import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.Repository.PortfolioRepo;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class PortfolioService {
    private final PortfolioRepo protRepo;
    private final AuthenticatedUserlogined isuserlogin;
    public ResponseEntity<?> saveportfolio(PortfolioDTO prot)
    {
        User user=isuserlogin.userlogined();
        // if user does not exist then crete it
        Portfolio portfolio=protRepo.findByUser(user).orElse(new Portfolio());
        portfolio.setTitle(prot.getTitle());
        portfolio.setBio(prot.getBio());
        portfolio.setLink(prot.getLink());
        portfolio.setBio(prot.getBio());
        portfolio.setAbout(prot.getAbout());
        portfolio.setProfileimage(prot.getProfileimage());
        portfolio.setTheme_id(prot.getTheme_id());
        portfolio.setUser(user);
        System.out.println(prot.getLink()+" "+prot.getTheme_id());
        System.out.println("protoploia is saved sucssfully till now... from print statement portservice");
        if(portfolio.getId()==null)
        {
            portfolio.setCreated_time(LocalDate.now());
        }
        portfolio.setUpdate_time(LocalDate.now());
        protRepo.save(portfolio); // checking is requried
        return ResponseEntity.ok("portfolio is saved Successfully");
    }
    public ResponseEntity<?>togglepublished()
    {
        User user=isuserlogin.userlogined();
        Portfolio portfolio=protRepo.findByUser(user).orElseThrow
                (()->new RuntimeException("portfolio can't find please created onces"));
        portfolio.set_published(!portfolio.is_published());
        protRepo.save(portfolio);
        String status=portfolio.is_published()?"Published":"Un-Published";
        return ResponseEntity.ok("Portfolio"+status+"Successfully");

    }
}
