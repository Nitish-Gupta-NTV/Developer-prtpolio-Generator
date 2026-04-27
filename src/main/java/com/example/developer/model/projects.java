package com.example.developer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
//@AllArgsConstructor
public class projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String title;
    private  String  description;
    private String github_url;
    private String   live_url;
    private String  image_url;
    private  boolean featured;
    private LocalDateTime created_at;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<skills> skillsList;
}
