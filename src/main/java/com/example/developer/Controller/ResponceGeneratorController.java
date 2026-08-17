package com.example.developer.Controller;

import com.example.developer.Service.ResponceGenearatorServices.PotopolioGeneratorservice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/responces")
public class ResponceGeneratorController {
    private final PotopolioGeneratorservice potopolioGeneratorservice;
    @GetMapping("/see") // this is public everyone see the this
    public ResponseEntity<?> portfoliogenerator()
    {

        return potopolioGeneratorservice.generateportfolio();
    }
    @GetMapping("/analytics")
    public ResponseEntity<?> getanalytics ()
    {
        System.out.println("from the controller request is reaching the analytics");
      return potopolioGeneratorservice.getAnalytics();
    }
}
