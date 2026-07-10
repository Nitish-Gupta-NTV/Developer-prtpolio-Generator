package com.example.developer.Service;

import com.example.developer.Repository.UserRepoitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.developer.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@AllArgsConstructor
public class AuthenticatedUserlogined {
    private final UserRepoitory repo;
    public User userlogined()
    {
        String email=SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        System.out.println("this is from the  usserloginedd method "+SecurityContextHolder.getContext().getAuthentication().getCredentials());
        System.out.println("this is from the userloginedmethod"+SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        return repo.findByEmail(email).orElseThrow(()->new RuntimeException("login User Not found"));
    }
}
