package com.example.developer.Controller;

import com.example.developer.DTO.skilluserDto;
import com.example.developer.DTO.skilluserWraperDTO;
import com.example.developer.Service.skilluserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/adddeveloper/skill")
public class skillusercontroller {
    private final skilluserService  skillservice;
    @PostMapping("/skills/save")
    public ResponseEntity<?> addedskill(@Valid @RequestBody skilluserWraperDTO skilldto)
    {
        System.out.println("reach the controller for the saving ths skill user");
        System.out.println("data from the controller"+skilldto);
         return skillservice.adduserskill(skilldto);
    }

}
