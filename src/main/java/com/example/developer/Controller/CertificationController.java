package com.example.developer.Controller;
import com.example.developer.DTO.CertificationDto;
import com.example.developer.Service.CertificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/certifocations")
public class CertificationController {
    private final CertificationService certificationService;
    @PostMapping("/add/certifications")
    public ResponseEntity<?> addcertifications(@Valid @RequestBody CertificationDto certificationDto)
    {
        System.out.println("data at the controller level"+certificationDto);
        return certificationService.savecerification(certificationDto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletecertications(@PathVariable Long id)
    {
        return certificationService.deletecerti(id);
    }
}

