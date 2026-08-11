package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "educations")
@Data
@NoArgsConstructor
public class educations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String educationlevel;
    private String institution;
    private String  educationame;
    private String grade;
    private Integer passingYear;
    private boolean ongoing;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
