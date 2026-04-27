package com.example.developer.Service;

import com.example.developer.Repository.UserRepoitory;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class userloginmethod implements UserDetailsService {
    private final UserRepoitory repoitory;
    @Override
    public  UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user=repoitory.findByEmail(email).orElseThrow(()->new
                UsernameNotFoundException("user not found with email "+email));
        System.out.println("role= we checking log= "+user.getRole());
        System.out.println(user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
               // List.of(new SimpleGrantedAuthority("Role_"+user.getRole()))
                List.of(new SimpleGrantedAuthority(user.getRole()))

        );

    }

}
