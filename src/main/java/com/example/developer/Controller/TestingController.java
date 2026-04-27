package com.example.developer.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/jwtauth")
@AllArgsConstructor
public class TestingController {
    @GetMapping("/testing")
    public ResponseEntity<?> onlyfortesting()
    {
        return ResponseEntity.ok("working fine");
    }
}
