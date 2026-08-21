package com.example.developer.Repository;

import com.example.developer.model.Conversation;
import com.example.developer.model.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConversationMessageRepo extends JpaRepository<ConversationMessage, Long> {
    List<ConversationMessage> findByConversationOrderByCreatedAtAsc(Conversation conversation);
}