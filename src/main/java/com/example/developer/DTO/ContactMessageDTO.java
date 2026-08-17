package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ContactMessageDTO {
    private Long id;
    private String senderName;
    private String senderEmail;
    private String subject;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
}
