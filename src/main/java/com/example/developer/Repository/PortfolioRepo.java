package com.example.developer.Repository;

import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {
    Optional<Portfolio>findByUser(User user);
   // boolean exitsByUser(User user);
   boolean existsByUser(User user);
    boolean existsBySlug(String slug);
    Optional<Portfolio> findBySlug(String slug);
    @Modifying
    @Query("UPDATE Portfolio p SET p.viewCount = p.viewCount + 1, p.lastViewedAt = CURRENT_TIMESTAMP WHERE p.slug = :slug")
    void incrementViewCount(@Param("slug") String slug);

}
