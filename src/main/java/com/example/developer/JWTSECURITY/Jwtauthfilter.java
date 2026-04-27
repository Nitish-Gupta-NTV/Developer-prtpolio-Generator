package com.example.developer.JWTSECURITY;

import com.example.developer.JWTSECURITY.jwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import com.example.developer.Service.userloginmethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class Jwtauthfilter extends OncePerRequestFilter {
    private final jwtUtils jwt;
    private final userloginmethod customuserdetailservices;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        System.out.println("enter the dofilter method ");

        String authHeader=request
                .getHeader("Authorization");

        if(authHeader==null||!authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }
        String token=authHeader.substring(7);
      //  if(jwt.validatetoken(token))
        if(jwt.validatetoken(token))
        {
            System.out.println(jwt.extractemailfromtoken(token));
            System.out.println(jwt.extractrolefromthetoken(token));

            String email=jwt.extractemailfromtoken(token);
            String role=jwt.extractrolefromthetoken(token);
            if(email!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
            {
                System.out.print(SecurityContextHolder.getContext().getAuthentication());
                UserDetails userDetails=customuserdetailservices.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }
        }
            filterChain.doFilter(request,response);
        System.out.println("dofilter passes sucessfully");
    }

}
