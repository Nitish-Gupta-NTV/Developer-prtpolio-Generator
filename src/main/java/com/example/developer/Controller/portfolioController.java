package com.example.developer.Controller;

import com.example.developer.DTO.PortfolioDTO;
import com.example.developer.DTO.SlugUpdateRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.developer.Service.Imlementservices.PortfolioService;

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
     }@PostMapping("/slug")
     public ResponseEntity<?>updatedslug(@Valid @RequestBody SlugUpdateRequestDTO dto)
     {
         System.out.println("enter the slug  for the updation ");
         return portfolioService.updateslug(dto.getSlug());

     }@PostMapping("/slug/check")
     public ResponseEntity<?>checkslug(@RequestBody String Slug)
     {

         System.out.println("requesst is reaching tocontroller and the for checkslug"+Slug);
         return portfolioService.checkSlugAvailability(Slug);
     }
    // @PostMapping("/ispublish")
    @PatchMapping("/ispublish")
    public ResponseEntity<?> toggle()
     {
         return portfolioService.togglepublished();
     }


}
