package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Portfolio
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String title;
    private String link;
    private String bio;
    private String about;
    private String profileimage;
    private Long theme_id;
    private boolean is_published;
    private LocalDate created_time;
    private LocalDate update_time;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
