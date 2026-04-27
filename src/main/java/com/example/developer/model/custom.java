package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom")
@Data
@NoArgsConstructor
public class custom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String decscribe;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
