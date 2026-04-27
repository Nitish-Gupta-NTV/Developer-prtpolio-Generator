package com.example.developer.JWTSECURITY;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
// for jwt we are using the emails not usename
@Component
public class jwtUtils {
    @Value("${jwt.secret}")
    private String JWT_Secret;
    @Value("${jwt.access-token-expiry}")
    private Long acesstokenexpery;

    private Key getsigningKey()
    {

        Key test= Keys.hmacShaKeyFor(JWT_Secret.getBytes());
        System.out.println("getsigningkeys"+test);
                return test;
    }
    public String GenerateAcessToken(String email,String role)
    {
        System.out.print("this is acess token expiry time"+acesstokenexpery);
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+acesstokenexpery))
                .signWith(getsigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    private Claims getclaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getsigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractemailfromtoken(String Token)
    {
        return getclaims(Token).getSubject();
    }
    public String extractrolefromthetoken(String token)
    {
        return getclaims(token).get("role",String.class);
    }
    public boolean validatetoken(String token)
    {
        try{
            getclaims(token);
            System.out.println("token validated sucessfully from the jwtUtils");
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT_Token get expired"+e.getMessage());

            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public boolean istokenexpired(String token)
    {
        try
        {
           return getclaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
