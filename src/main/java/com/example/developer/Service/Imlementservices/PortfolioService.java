package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.PortfolioDTO;
import com.example.developer.GlobalExceptionHandler.PortfolioNotFoundException;
import com.example.developer.GlobalExceptionHandler.PortfolioNotPublishedException;
import com.example.developer.GlobalExceptionHandler.SlugAlreadyTakenException;
import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.Repository.PortfolioRepo;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor

public class PortfolioService {
    private final PortfolioRepo protRepo;
    private final AuthenticatedUserlogined isuserlogin;
    private static final Set<String> RESERVED_SLUGS = Set.of(
            "login", "signup", "dashboard", "admin", "api", "settings",
            "public", "app", "auth", "logout", "profile", "wizard"
    );
    //@Value("${Backend_URl}")
//private String backendurl;
    public ResponseEntity<?> saveportfolio(PortfolioDTO prot)
    {
        User user=isuserlogin.userlogined();
        // if user does not exist then crete it
        Portfolio portfolio=protRepo.findByUser(user).orElse(new Portfolio());
        boolean isnewprtopolio=protRepo.findByUser(user).isEmpty();
        portfolio.setHeadline(prot.getHeadline());
        portfolio.setBio(prot.getBio());
        portfolio.setLocation(prot.getLocation());
        portfolio.setBio(prot.getBio());
        portfolio.setAbout(prot.getAbout());
        portfolio.setProfileimage(prot.getProfileimage());
        portfolio.setTheme_id(prot.getTheme_id());
        if(isnewprtopolio)
        {
            portfolio.setSlug(generateuniqueslug(user.getName()));
        }
        portfolio.setUser(user);
        System.out.println(prot.getLocation()+" "+prot.getTheme_id());

        System.out.println("protoploia is saved sucssfully till now... from print statement portservice");
       /*if(portfolio.getId()==null)
        {
            portfolio.setCreated_time(LocalDate.now());
        }
        portfolio.setUpdate_time(LocalDate.now());*/
        protRepo.save(portfolio); // checking is requried
        return ResponseEntity.ok(Map.of(
                "message", "Portfolio saved successfully",
                "slug", portfolio.getSlug(),
                "publicUrl", "https://yourapp.com/p/" + portfolio.getSlug()
        ));
    }
    public ResponseEntity<?>togglepublished()
    {
        User user=isuserlogin.userlogined();
        Portfolio portfolio=protRepo.findByUser(user).orElseThrow
                (()->new RuntimeException("portfolio can't find please created onces"));
        portfolio.set_published(!portfolio.is_published());
        protRepo.save(portfolio);
        String status=portfolio.is_published()?"Published":"Un-Published";
       // return ResponseEntity.ok("Portfolio"+status+"Successfully");
        return ResponseEntity.ok(
                Map.of(
                        "message", "Publish status updated"+status+" "
                )
        );

    }
    public String generateuniqueslug(String name)
    {
        String base=SlugGenerator.generateBaseSlug(name);
        String slug;
        int attempts=0;
        do{
            slug=base+"-"+SlugGenerator.generateRandomSuffix(8);
            attempts++;
            if(attempts>10)
            {
                throw new IllegalStateException("could not generate the slug url");
            }

        }while(protRepo.existsBySlug(slug));

        return slug;
    }
    public ResponseEntity<?> updateslug(String newslug)
    {

        User user=isuserlogin.userlogined();

        if(RESERVED_SLUGS.contains(newslug))
        {
            throw new IllegalArgumentException("this slug is reserved pleases choose another slug");

        }
        Portfolio portfoliofind=protRepo.findByUser(user).orElseThrow(()->new PortfolioNotFoundException("prtopolio not found"));
         if(newslug.equals(portfoliofind.getSlug()))
         {
             return ResponseEntity.ok("slug Unchangedd");
         }
         if(protRepo.existsBySlug(newslug))
         {
             throw new SlugAlreadyTakenException("slug alreadyTaken Exception");
         }
        if (!isSlugAvailable(newslug)) {

            if (RESERVED_SLUGS.contains(newslug)) {
                throw new IllegalArgumentException(
                        "This slug is reserved. Please choose another slug"
                );
            }

            throw new SlugAlreadyTakenException(
                    "Slug is already taken"
            );
        }
         portfoliofind.setSlug(newslug);
         protRepo.save(portfoliofind);
        return ResponseEntity.ok(Map.of(
                "message", "Slug updated successfully",
                "slug", portfoliofind.getSlug(),
                "publicUrl", "backendurl"+ portfoliofind.getSlug()
        ));
    }
    public ResponseEntity<?> checkSlugAvailability(String slug) {

        boolean available = isSlugAvailable(slug);

        return ResponseEntity.ok(
                Map.of("available", available)
        );


    }
    private boolean isSlugAvailable(String slug) {
        boolean reserved = RESERVED_SLUGS.contains(slug);
        boolean exists = protRepo.existsBySlug(slug);

        return !reserved && !exists;
    }



    }


