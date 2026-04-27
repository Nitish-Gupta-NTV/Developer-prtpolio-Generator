package com.example.developer.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class forgotpassword {
    @NotBlank
    @Email(message = "email is not valid ")
     private String email;
}
