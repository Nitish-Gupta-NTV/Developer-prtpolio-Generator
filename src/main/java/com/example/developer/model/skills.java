package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="skills")
public class skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skills_name;
    private String levels;
   @ManyToOne
    @JoinColumn( nullable = false)
    private projects project;
    /*
    add by the chatgpt
    */



}
