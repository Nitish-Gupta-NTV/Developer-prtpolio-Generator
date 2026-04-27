package com.example.developer.Repository;

import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.developer.model.projects;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<projects,Long> {

    List<projects> findByUser(User user);
    List<projects> findByUserAndFeatured(User user, boolean featured);
}
