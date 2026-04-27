package com.example.developer.Service;

import com.example.developer.Repository.CertaficationsRepo;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.developer.DTO.CertificationDto;
import com.example.developer.model.certaficatios;
@AllArgsConstructor
@Service
@Data
public class CertificationService {
    private final AuthenticatedUserlogined isuserlogined;
    private final CertaficationsRepo certirepo;
    // add certifcation
    public ResponseEntity<?>savecerification(CertificationDto certidto)
    {
        User user=isuserlogined.userlogined();
        certaficatios certi=new certaficatios();
        certi.setTitle(certidto.getTitle());
        certi.setDescscribe(certidto.getDescscribe());
        certi.setIssuer(certidto.getIssuer());
        certi.setIssued_date(certidto.getIssued_date());
        certi.setUser(user);
        certirepo.save(certi);
        return ResponseEntity.ok("Certification added successfully");
    }
    // delete certification
    public ResponseEntity<?>deletecerti(Long id)
    {
        User user=isuserlogined.userlogined();
        certaficatios certi=certirepo.findById(id).orElseThrow(()->new RuntimeException("no certification found"));
        if(!certi.getUser().getId().equals(user.getId()))
        {
            return ResponseEntity.status(403).body("Not allowed to perfrom the taks");
        }
        certirepo.delete(certi);
        return ResponseEntity.ok("certification deleted sucessfully");
    }



}
