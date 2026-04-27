package com.example.developer.Controller;

import com.example.developer.DTO.Login;
import com.example.developer.DTO.Refreshtokenrequset;
import com.example.developer.DTO.Register;
import com.example.developer.DTO.forgotpassword;
import com.example.developer.DTO.resetpassword;
import com.example.developer.Service.PotopolioGeneratorservice;
import com.example.developer.Service.Userregitery;
import com.example.developer.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserAuthentication {

    private Userregitery services_function;
    private final PotopolioGeneratorservice potopolioGeneratorservice;
    @GetMapping("/see") // this is public everyone see the this
    public ResponseEntity<?> portfoliogenerator()
    {

        return potopolioGeneratorservice.generateportfolio();
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Register resgister)
    {
        System.out.println("request being hitting the register controller ");
        try {
            User user = new User();
            user.setPassword(resgister.getPassword());
            user.setEmail(resgister.getEmail());
            user.setName(resgister.getName());
            user.setUsername(resgister.getUser_name());
            user.setPhonenumber(resgister.getPhone_number());
            user.setRole(resgister.getRole());
            return services_function.regsiteruser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("catch block executed"+e.getMessage());
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> loginuser(@Valid @RequestBody Login request)
    {
        System.out.println("entering the login section");
        return services_function.loginuser(request);

    }@PostMapping("/refresh-token")
    public ResponseEntity<?> getregreshtoken(@Valid @RequestBody Refreshtokenrequset requset)
    {
        return services_function.refreshtoken(requset);
    }
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetpassword_functions(@Valid @RequestBody forgotpassword requset)
    {
        return services_function.forgetpassword_method(requset);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetpassword_function(@Valid @RequestBody resetpassword requset)
    {
        return services_function.restpassword_method(requset);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout_function(@AuthenticationPrincipal UserDetails requset)
    {
        return services_function.logout_Method(requset.getUsername());
    }
    // only for the testing


    // need to developed
    @GetMapping("/developer/profile")
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<?> developerProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("Welcome Developer: " + userDetails.getUsername());
    }
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminDashboard() {
        return ResponseEntity.ok("Welcome Admin!");
    }

}
