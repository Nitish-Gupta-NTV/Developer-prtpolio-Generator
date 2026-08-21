package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.*;
import com.example.developer.Repository.ConversationMessageRepo;
import com.example.developer.Repository.ConversationRepo;
import com.example.developer.Repository.PortfolioRepo;
import com.example.developer.GlobalExceptionHandler.PortfolioNotFoundException;
import com.example.developer.model.Conversation;
import com.example.developer.model.ConversationMessage;
import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConversationService {

    private final ConversationRepo conversationRepo;
    private final ConversationMessageRepo messageRepo;
    private final PortfolioRepo portfolioRepo;
    private final AuthenticatedUserlogined isuserlogin;
    private final JavaMailSender mailSender;


    private static final String FRONTEND_BASE_URL = "http://localhost:5173"; // move to application.properties later
  //@Value("${frontend_url}")
   //private  String FRONTEND_BASE_URL;
    // ---------- Visitor starts a new conversation ----------
    public ResponseEntity<?> startConversation(String slug, StartConversationRequestDTO dto) {
        Portfolio portfolio = portfolioRepo.findBySlug(slug)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));

        Conversation convo = new Conversation();
        convo.setVisitorName(dto.getName());
        convo.setVisitorEmail(dto.getEmail());
        convo.setSubject(dto.getSubject());
        convo.setAccessToken(UUID.randomUUID().toString());
        convo.setPortfolio(portfolio);
        convo.setOwnerUnread(true);
        conversationRepo.save(convo);

        ConversationMessage firstMsg = new ConversationMessage();
        firstMsg.setSender(ConversationMessage.Sender.VISITOR);
        firstMsg.setBody(dto.getMessage());
        firstMsg.setConversation(convo);
        messageRepo.save(firstMsg);

        sendMailSafely(
                portfolio.getUser().getEmail(),
                "New message: " + dto.getSubject(),
                "From " + dto.getName() + " <" + dto.getEmail() + ">\n\n" + dto.getMessage() +
                        "\n\nReply from your dashboard: " + FRONTEND_BASE_URL + "/dashboard/messages",
                null
        );

        sendMailSafely(
                dto.getEmail(),
                "We received your message",
                "Thanks for reaching out — you'll get a reply soon.\n\n" +
                        "View this conversation any time: " + FRONTEND_BASE_URL + "/conversation/" + convo.getAccessToken(),
                null
        );

        return ResponseEntity.ok(Map.of("message", "Message sent successfully"));
    }

    // ---------- Owner: list conversations ----------
    public ResponseEntity<?> getMyConversations() {
        Portfolio portfolio = getMyPortfolio();
        List<ConversationSummaryDTO> list = conversationRepo.findByPortfolioOrderByUpdatedAtDesc(portfolio).stream()
                .map(c -> {
                    List<ConversationMessage> msgs = messageRepo.findByConversationOrderByCreatedAtAsc(c);
                    String preview = msgs.isEmpty() ? "" : msgs.get(msgs.size() - 1).getBody();
                    if (preview.length() > 60) preview = preview.substring(0, 60) + "...";
                    return new ConversationSummaryDTO(c.getId(), c.getVisitorName(), c.getSubject(), preview, c.getUpdatedAt(), c.isOwnerUnread());
                })
                .toList();
        return ResponseEntity.ok(list);
    }

    // ---------- Owner: view + auto-mark-read one conversation ----------
    public ResponseEntity<?> getConversationDetail(Long id) {
        Conversation convo = getOwnedConversation(id);
        convo.setOwnerUnread(false);
        conversationRepo.save(convo);
        return ResponseEntity.ok(toDetailDto(convo));
    }

    // ---------- Owner: reply from dashboard ----------
    public ResponseEntity<?> ownerReply(Long id, ReplyRequestDTO dto) {
        Conversation convo = getOwnedConversation(id);

        ConversationMessage msg = new ConversationMessage();
        msg.setSender(ConversationMessage.Sender.OWNER);
        msg.setBody(dto.getMessage());
        msg.setConversation(convo);
        messageRepo.save(msg);

        convo.setOwnerUnread(false);
        convo.setUpdatedAt(java.time.LocalDateTime.now());
        conversationRepo.save(convo);

        sendMailSafely(
                convo.getVisitorEmail(),
                "New reply: " + convo.getSubject(),
                "You have a new reply.\n\nView it here: " + FRONTEND_BASE_URL + "/conversation/" + convo.getAccessToken(),
                null
        );

        return ResponseEntity.ok(toDetailDto(convo));
    }

    public ResponseEntity<?> getUnreadCount() {
        Portfolio portfolio = getMyPortfolio();
        return ResponseEntity.ok(Map.of("unreadCount", conversationRepo.countByPortfolioAndOwnerUnreadTrue(portfolio)));
    }

    // ---------- Public: visitor views their conversation via token ----------
    public ResponseEntity<?> getPublicConversation(String token) {
        Conversation convo = conversationRepo.findByAccessToken(token)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        return ResponseEntity.ok(toDetailDto(convo));
    }

    // ---------- Public: visitor replies via token ----------
    public ResponseEntity<?> visitorReply(String token, ReplyRequestDTO dto) {
        Conversation convo = conversationRepo.findByAccessToken(token)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        ConversationMessage msg = new ConversationMessage();
        msg.setSender(ConversationMessage.Sender.VISITOR);
        msg.setBody(dto.getMessage());
        msg.setConversation(convo);
        messageRepo.save(msg);

        convo.setOwnerUnread(true);
        convo.setUpdatedAt(java.time.LocalDateTime.now());
        conversationRepo.save(convo);

        sendMailSafely(
                convo.getPortfolio().getUser().getEmail(),
                "New reply: " + convo.getSubject(),
                convo.getVisitorName() + " replied.\n\nCheck your dashboard: " + FRONTEND_BASE_URL + "/dashboard/messages",
                null
        );

        return ResponseEntity.ok(toDetailDto(convo));
    }

    // ---------- helpers ----------
    private Portfolio getMyPortfolio() {
        User user = isuserlogin.userlogined();
        return portfolioRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }

    private Conversation getOwnedConversation(Long id) {
        Portfolio portfolio = getMyPortfolio();
        Conversation convo = conversationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        if (!convo.getPortfolio().getId().equals(portfolio.getId())) {
            throw new RuntimeException("Not authorized to view this conversation");
        }
        return convo;
    }

    private ConversationDetailDTO toDetailDto(Conversation convo) {
        List<MessageDTO> messages = messageRepo.findByConversationOrderByCreatedAtAsc(convo).stream()
                .map(m -> new MessageDTO(m.getSender().name(), m.getBody(), m.getCreatedAt()))
                .toList();
        return new ConversationDetailDTO(convo.getId(), convo.getSubject(), convo.getVisitorName(), convo.getVisitorEmail(), convo.isOwnerUnread(), messages);
    }

    private void sendMailSafely(String to, String subject, String text, String replyTo) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(text);
            if (replyTo != null) mail.setReplyTo(replyTo);
            mailSender.send(mail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}