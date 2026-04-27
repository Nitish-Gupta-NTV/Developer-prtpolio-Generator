package com.example.developer.Repository;

import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {
    Optional<Portfolio>findByUser(User user);
   // boolean exitsByUser(User user);
   boolean existsByUser(User user);
}
