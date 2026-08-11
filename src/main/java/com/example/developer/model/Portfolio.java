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
    private String headline;
    private String location;
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
    @PrePersist
    protected void onCreate() {
        LocalDate today = LocalDate.now();

        this.created_time = today;
        this.update_time = today;
    }
    @PreUpdate
    protected void onUpdate() {
        this.update_time = LocalDate.now();
    }


}
