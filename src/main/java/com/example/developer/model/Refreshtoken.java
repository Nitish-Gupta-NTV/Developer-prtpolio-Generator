package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "refresh_token")
public class Refreshtoken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
