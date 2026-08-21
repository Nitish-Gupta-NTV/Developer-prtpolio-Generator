package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConversationSummaryDTO {
    private Long id;
    private String visitorName;
    private String subject;
    private String lastMessagePreview;
    private LocalDateTime updatedAt;
    private boolean ownerUnread;
}