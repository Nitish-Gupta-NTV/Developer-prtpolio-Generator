package com.example.developer.Repository;

import com.example.developer.model.skills;
import com.example.developer.model.projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepo extends JpaRepository<skills,Long> {

    List<skills> findByProject(projects project);



}
