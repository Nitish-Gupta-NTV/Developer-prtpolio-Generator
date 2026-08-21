package com.example.developer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDTO {
    private String sender; // "VISITOR" or "OWNER"
    private String body;
    private LocalDateTime createdAt;
}