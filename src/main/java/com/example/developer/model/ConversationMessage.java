package com.example.developer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ConversationMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Sender { VISITOR, OWNER }

    @Enumerated(EnumType.STRING)
    private Sender sender;

    @Column(length = 2000)
    private String body;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}