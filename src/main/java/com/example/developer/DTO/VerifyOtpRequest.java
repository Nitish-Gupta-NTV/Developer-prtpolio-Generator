package com.example.developer.DTO;

import com.example.developer.model.User;
import lombok.Data;

@Data
public class VerifyOtpRequest {
   private Register userdata;
    private String otp;
}
