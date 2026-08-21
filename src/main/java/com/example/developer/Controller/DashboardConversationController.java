package com.example.developer.Controller;

import com.example.developer.DTO.ReplyRequestDTO;
import com.example.developer.Service.Imlementservices.ConversationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Authenticated
@RestController
@RequestMapping("/api/developer/portfolio/messages")
@AllArgsConstructor
public class DashboardConversationController {
    private final ConversationService conversationService;

    @GetMapping
    public ResponseEntity<?> listConversations() {
        return conversationService.getMyConversations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConversation(@PathVariable Long id) {
        return conversationService.getConversationDetail(id);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<?> reply(@PathVariable Long id, @Valid @RequestBody ReplyRequestDTO dto) {
        return conversationService.ownerReply(id, dto);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<?> unreadCount() {
        return conversationService.getUnreadCount();
    }
}
