package com.example.developer.SpringSecurity;

import com.example.developer.JWTSECURITY.Jwtauthfilter;
import com.example.developer.Service.userloginmethod;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class Springauth {
    private final Jwtauthfilter jwtfilter;
    private final userloginmethod custromuserloginmethod;
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception
    {
        System.out.println("entering the user authentications");
        DaoAuthenticationProvider Provider=new DaoAuthenticationProvider();
        Provider.setUserDetailsService(custromuserloginmethod);
        Provider.setPasswordEncoder(passwordEncoder());
        return Provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        System.out.println("enter in the seecurity filter chain from the springauth");
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/api/developer/**").hasRole("Developer")
                        .requestMatchers("/api/developer/**").hasRole("DEVELOPER")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        System.out.println("passed the springsecurity filter chain sucessfully");
        return http.build();



    }
}
