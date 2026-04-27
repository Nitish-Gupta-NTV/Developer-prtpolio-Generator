package com.example.developer.Repository;

import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.developer.model.educations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationRepo extends JpaRepository<educations,Long> {

    Optional<educations> findByUser(User user);
}
