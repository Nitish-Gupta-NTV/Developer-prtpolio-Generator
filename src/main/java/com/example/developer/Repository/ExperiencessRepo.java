package com.example.developer.Repository;

import com.example.developer.model.User;
import com.example.developer.model.experiences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExperiencessRepo extends JpaRepository<experiences,Long> {

    List<experiences> findByUser(User user);
}
