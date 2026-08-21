package com.example.developer.Controller;

import com.example.developer.DTO.*;
import com.example.developer.Service.Imlementservices.OtpService;
import com.example.developer.Service.Imlementservices.Userregitery;
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
    private OtpService otp;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody VerifyOtpRequest resgister)
    {
        System.out.println("request being hitting the register controller ");
         System.out.println("data received at controller"+resgister);

            return services_function.regsiteruser(resgister);
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

    @PostMapping("/register/send-otp")
    public ResponseEntity<?> sendRegistrationOtp(@Valid @RequestBody Register register)
    {
       return otp.validateduser(register);

    }
}
