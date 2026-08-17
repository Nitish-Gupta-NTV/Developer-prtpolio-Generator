package com.example.developer.Controller;

import com.example.developer.Service.Imlementservices.ContactMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developer/portfolio/messages")
@AllArgsConstructor
public class ContactController {
    private final ContactMessageService messageService;
    @GetMapping("/getmessage")
    public ResponseEntity<?>getmessage()
    {
        System.out.println("entering the controller form the message inbox");
       return messageService.getMymessage();
    }
    @PatchMapping("/{id}/read")
    public ResponseEntity<?>markasread(@PathVariable Long id)
    {
        return messageService.markasread(id);
    }
    @GetMapping("/unread-count")
    public ResponseEntity<?>unreadmessage()
    {
        return messageService.getunreadCount();
    }
}
