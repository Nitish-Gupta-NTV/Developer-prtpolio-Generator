package com.example.developer.Repository;

import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.developer.model.socialmedia;
import org.springframework.stereotype.Repository;
import java.util.Optional;

     @Repository
  public interface socialmediaRepo extends JpaRepository<socialmedia,Long> {

   Optional<socialmedia> findByUser(User user);
}
