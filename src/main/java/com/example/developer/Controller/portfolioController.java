package com.example.developer.Controller;

import com.example.developer.DTO.PortfolioDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.developer.Service.PortfolioService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/portfolio")
public class portfolioController {
    private final PortfolioService portfolioService;



    @PostMapping("/save")
     public ResponseEntity<?>  saveportopolio(@Valid @RequestBody PortfolioDTO resoponce)
     {
         System.out.println("data from the controller which is send by the post man");
         System.out.println("data the coontroller"+resoponce);
        return portfolioService.saveportfolio(resoponce);
     }
     @PostMapping("/ispublish")
    public ResponseEntity<?> toggle()
     {
         return portfolioService.togglepublished();
     }

}
