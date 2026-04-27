package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "certification")
public class certaficatios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String descscribe;
    private String issuer;
    private LocalDate issued_date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
