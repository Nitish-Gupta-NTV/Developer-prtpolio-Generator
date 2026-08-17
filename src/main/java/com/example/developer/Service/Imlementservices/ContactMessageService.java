package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.ContactMessageDTO;
import com.example.developer.DTO.ContactRequestDTO;
import com.example.developer.GlobalExceptionHandler.PortfolioNotFoundException;
import com.example.developer.Repository.ContactMessageRepo;
import com.example.developer.Repository.PortfolioRepo;
import com.example.developer.model.ContactMessage;
import com.example.developer.model.Portfolio;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Data
public class ContactMessageService {
    private final AuthenticatedUserlogined isuserlogined;
    private final ContactMessageRepo messagerepo;
    private final PortfolioRepo prortRepo;
    private final JavaMailSender mailSender;
public ResponseEntity<?>submitcontact(String Slug,ContactRequestDTO dto)
    {
        Portfolio port= prortRepo.findBySlug(Slug).orElseThrow(()-> new RuntimeException("invalid url"));
            ContactMessage contactMessage=new ContactMessage();
            contactMessage.setSenderName(dto.getName());
            contactMessage.setSenderEmail(dto.getEmail());
            contactMessage.setMessage(dto.getMessage());
            contactMessage.setMessage(dto.getMessage());
            contactMessage.setPortfolio(port);
            messagerepo.save(contactMessage);
            try{
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(port.getUser().getEmail());
                mailMessage.setSubject("New PortPoilo Message"+dto.getSubject());
                mailMessage.setText("You received a new message on your portfolio.\n\n" +
                        "From: " + dto.getName() + " <" + dto.getEmail() + ">\n\n" +
                        dto.getMessage() + "\n\n" +"Reply directly to this email to respond, or check your dashboard inbox.");
                mailMessage.setReplyTo(dto.getEmail());

                System.out.println("mail send to this mail"+  port.getUser().getEmail());
                mailSender.send(mailMessage);
            } catch (Exception e) {
                System.out.println("falied to send mail"+e.getMessage());
            }
            return ResponseEntity.ok(Map.of("message","mail send sucessfully"));

    }

    public ResponseEntity<?>getMymessage()
    {
        System.out.println("enter the services layer message inbox");
        User user=isuserlogined.userlogined();
        Portfolio port=prortRepo.findByUser(user).orElseThrow(()->new RuntimeException("prtopolio not found exception"));
        System.out.println("prortpolio from the mesageinbox"+port);
        List<ContactMessageDTO> dtos=messagerepo.findByPortfolioOrderByCreatedAtDesc(port).stream()
                .map(m->new ContactMessageDTO(m.getId(), m.getSenderName(), m.getSenderEmail(), m.getSubject(), m.getMessage(), m.isRead(), m.getCreatedAt()))
                .toList();
        System.out.println("prortpoliodtoslist from the mesageinbox"+dtos);
        return ResponseEntity.ok(dtos);

    }

public ResponseEntity<?> markasread(Long Messageid)
{
    ContactMessage conmessage=messagerepo.findById(Messageid).orElseThrow(()->new RuntimeException("message Not found"));
    conmessage.setRead(true);
    messagerepo.save(conmessage);
    return ResponseEntity.ok().build();

}
    public ResponseEntity<?> getunreadCount()
    {
        User user=isuserlogined.userlogined();
        Portfolio port=prortRepo.findByUser(user).orElseThrow(()-> new PortfolioNotFoundException("PortfoiloNot found Exception"));
        return ResponseEntity.ok(Map.of("UnReadCount",messagerepo.countByPortfolioAndIsReadFalse(port)));
    }
}
