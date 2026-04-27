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
    private String high_schooling;
    private String secondary_schooling;
    private String bachelor;
    private String postgraducation;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
