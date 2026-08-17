package com.example.developer.Controller;

import com.example.developer.DTO.ContactRequestDTO;
import com.example.developer.Service.Imlementservices.ContactMessageService;
import com.example.developer.Service.Imlementservices.PortfolioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.developer.Service.ResponceGenearatorServices.PotopolioGeneratorservice;

@RestController
@AllArgsConstructor
@RequestMapping("/api/portfolio")
public class PublicPortfolioController {
    private final PotopolioGeneratorservice portfolioService;
    private ContactMessageService messageService;

    @GetMapping("/public/{slug}")
    public ResponseEntity<?> getPublicPortfolio(@PathVariable String slug) {
        System.out.println("slug ");
        return portfolioService.getPublicPortfolio(slug);
    }
    @PostMapping("/public/{slug}/contact")
    public ResponseEntity<?>submitcontact(@PathVariable String slug ,@Valid@RequestBody ContactRequestDTO dto)
    {
        return messageService.submitcontact(slug,dto);
       // return messageService.submitcontact(slug,dto);

    }
}
