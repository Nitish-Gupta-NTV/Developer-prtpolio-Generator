package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "socialmedia_links")
@Data
@NoArgsConstructor
public class socialmedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkedine;
    private String github;
    private String codingp_platform;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
