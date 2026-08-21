package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ConversationDetailDTO {
    private Long id;
    private String subject;
    private String visitorName;
    private String visitorEmail;
    private boolean ownerUnread;
    private List<MessageDTO> messages;
}