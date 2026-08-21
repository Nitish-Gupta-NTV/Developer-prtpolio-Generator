package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.Register;
import com.example.developer.Repository.EmailOtpRepository;
import com.example.developer.Repository.UserRepoitory;
import com.example.developer.model.EmailOtp;
import com.example.developer.model.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final EmailOtpRepository otpRepository;
    private final JavaMailSender mailSender;
    private final UserRepoitory repo;

    @Transactional
    public void sendOtp(String email) {

        otpRepository.deleteByEmail(email);

        String otp = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 1000000)
        );
        System.out.println("your email otp " + otp);

        LocalDateTime expiresAt =
                LocalDateTime.now().plusMinutes(5);

        EmailOtp emailOtp =
                new EmailOtp(email, otp, expiresAt);

        otpRepository.save(emailOtp);

        sendEmail(email, otp);
    }

    private void sendEmail(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your Registration OTP");
        message.setText(
                "Your OTP for registration is: " + otp +
                        "\n\nThis OTP will expire in 5 minutes."
        );

        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {
      System.out.println("email and otp at the verify otp"+email+otp);
        Optional<EmailOtp> optionalOtp =
                otpRepository.findTopByEmailOrderByIdDesc(email);

        if (optionalOtp.isEmpty()) {
            System.out.println("otp is empty");
            return false;
        }

        EmailOtp emailOtp = optionalOtp.get();
        System.out.println("DB OTP: [" + emailOtp.getOtp() + "]");
        System.out.println("DB EMAIL: " + emailOtp.getEmail());
        System.out.println("Verified: " + emailOtp.isVerified());
        System.out.println("Expires: " + emailOtp.getExpiresAt());
        System.out.println("Current: " + LocalDateTime.now());

        if (emailOtp.isVerified()) {
            System.out.println("otp is alredy verified");
            return false;
        }

        if (emailOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            System.out.println("otp is expired");
            return false;
        }

        if (!emailOtp.getOtp().equals(otp)) {
            System.out.println("otp doesnot match");
            return false;
        }

        emailOtp.setVerified(true);
        otpRepository.save(emailOtp);

        return true;
    }
    @Transactional
    public ResponseEntity<?> validateduser(Register register) {
        System.out.println("entring the services layer");
        System.out.println(register);
        if (repo.existsByUsername(register.getUser_name())) {
            return ResponseEntity.badRequest().body("UserName is Not available");
        }

        if (repo.existsByEmail(register.getEmail())) {
            return ResponseEntity.badRequest().body("email already registerd");
        }
        if (repo.existsByPhonenumber(register.getPhone_number())) {
            return ResponseEntity.badRequest().body("phone number is already exits");
        }
        if (!isValidPassword(register.getPassword())) {

            return ResponseEntity.badRequest().body("password is not valid");
        }

        System.out.println("reach the otp sending state");
        sendOtp(register.getEmail());// it is used to send  the otp
        System.out.println("otp send to mail = "+register.getEmail());

        return ResponseEntity.ok("OTP sent successfully");

    }
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";;
        return password.matches(regex);

    }
}
