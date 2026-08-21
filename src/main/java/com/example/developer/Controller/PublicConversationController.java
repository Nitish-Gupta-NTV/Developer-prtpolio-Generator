package com.example.developer.Controller;

import com.example.developer.DTO.ContactRequestDTO;
import com.example.developer.DTO.StartConversationRequestDTO;
import com.example.developer.DTO.ReplyRequestDTO;
import com.example.developer.Service.Imlementservices.ConversationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.developer.Service.ResponceGenearatorServices.PotopolioGeneratorservice;

@RestController
@AllArgsConstructor
@RequestMapping("/api/portfolio")
public class PublicConversationController {
    private final PotopolioGeneratorservice portfolioService;
    private ConversationService messageService;


    @GetMapping("/public/{slug}")
    public ResponseEntity<?> getPublicPortfolio(@PathVariable String slug) {
        System.out.println("slug ");
        return portfolioService.getPublicPortfolio(slug);
    }
    @PostMapping("/public/{slug}/contact")
    public ResponseEntity<?> startConversation(@PathVariable String slug, @Valid @RequestBody StartConversationRequestDTO dto) {
        return messageService.startConversation(slug, dto);
    }

    @GetMapping("/public/conversation/{token}")
    public ResponseEntity<?> getConversation(@PathVariable String token) {
        return messageService.getPublicConversation(token);
    }

    @PostMapping("/public/conversation/{token}/reply")
    public ResponseEntity<?> visitorReply(@PathVariable String token, @Valid @RequestBody ReplyRequestDTO dto) {
        return messageService.visitorReply(token, dto);
    }
}

