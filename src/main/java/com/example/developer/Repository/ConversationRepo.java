package com.example.developer.Repository;

import com.example.developer.model.Conversation;
import com.example.developer.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    List<Conversation> findByPortfolioOrderByUpdatedAtDesc(Portfolio portfolio);
    Optional<Conversation> findByAccessToken(String token);
    long countByPortfolioAndOwnerUnreadTrue(Portfolio portfolio);
}