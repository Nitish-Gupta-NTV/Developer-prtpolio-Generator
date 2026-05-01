package com.example.developer.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column(name = "username", nullable = false, unique = true)
   private String username;
    @Column(nullable = false)
   private String password;
    @Column(nullable = false)
   private String role;
    @Column(nullable = false)
   private String name;
    @Column(name = "phonenumber", nullable = false)
   private Long phonenumber;
    @Column(nullable = false)
   private String email;
    @Column(name = "reset_token")
    private String resetToken;
    @Column(name="reset_token_exp")
    private LocalDateTime resetTokenExpiry;

}
