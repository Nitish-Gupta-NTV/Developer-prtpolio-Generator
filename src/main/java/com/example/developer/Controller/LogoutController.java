package com.example.developer.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.developer.Service.Imlementservices.Userregitery;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LogoutController {
    private  Userregitery services_function;
    @PostMapping("/logout")
    public ResponseEntity<?> logout_function(@AuthenticationPrincipal UserDetails requset)
    {
        return services_function.logout_Method(requset.getUsername());
    }
}
