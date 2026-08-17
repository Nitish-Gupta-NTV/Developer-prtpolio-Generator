package com.example.developer.SpringSecurity;

import com.example.developer.JWTSECURITY.Jwtauthfilter;
import com.example.developer.Service.Imlementservices.userloginmethod;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpMethod;
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
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
@EnableWebSecurity
@EnableMethodSecurity
//@AllArgsConstructor
@RequiredArgsConstructor
public class Springauth {
    private final Jwtauthfilter jwtfilter;
    private final userloginmethod custromuserloginmethod;
    @Value("${frontend_url}")
    private String frontend_url;
    @Value("${ipurl}")
    private String ipurl;
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontend_url,ipurl));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlsource=new UrlBasedCorsConfigurationSource();
        urlsource.registerCorsConfiguration("/**",config);
        return urlsource;

    }
    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception
    {
        System.out.println("enter the authentication provider com.example.developer.SpringSecurity");

        System.out.println("entering the user authentications");
        DaoAuthenticationProvider Provider=new DaoAuthenticationProvider();
        Provider.setUserDetailsService(custromuserloginmethod);
        Provider.setPasswordEncoder(passwordEncoder());
        return Provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config) throws Exception
    {
        System.out.println("enter the authentication Manager com.example.developer.SpringSecurity");
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        System.out.println("enter in the seecurity filter chain from the springauth com.example.developer.SpringSecurity");
        http
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers("/api/portfolio/public/**").permitAll()
                       // .requestMatchers("/api/auth/logout").authenticated()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/api/developer/**").hasRole("Developer")
                        .requestMatchers("/api/portfolio/public/**").permitAll()
                        .requestMatchers("/api/developer/**").hasRole("DEVELOPER")
                       // .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        System.out.println("passed the springsecurity filter chain sucessfully");
        return http.build();



    }
}
