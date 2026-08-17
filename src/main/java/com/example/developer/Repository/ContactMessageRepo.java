package com.example.developer.Repository;

import com.example.developer.model.ContactMessage;
import com.example.developer.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContactMessageRepo extends JpaRepository<ContactMessage,Long> {
    List<ContactMessage> findByPortfolioOrderByCreatedAtDesc(Portfolio portfolio);
    long countByPortfolioAndIsReadFalse(Portfolio portfolio);
}
