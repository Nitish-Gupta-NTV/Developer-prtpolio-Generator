package com.example.developer.Service.Imlementservices;

import com.example.developer.DTO.*;
import com.example.developer.JWTSECURITY.jwtUtils;
import com.example.developer.Repository.EmailOtpRepository;
import com.example.developer.Repository.RefreshtokenRepository;
import com.example.developer.Repository.UserRepoitory;
import com.example.developer.model.Refreshtoken;
import com.example.developer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Userregitery {
    private final UserRepoitory repo;
    private final RefreshtokenRepository refreshtokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtils jwt;
    private final AuthenticationManager authenticationManager;
    private final EmailOtpRepository otpRepository;
    private final JavaMailSender mailSender;
    private final OtpService otpService;


    @Value("${jwt.refresh-token-expiry}")
    private long refteshtokenexpiry;
    @Value("${frontend_url}")
    private String frontendurl;



    public ResponseEntity<?> regsiteruser(VerifyOtpRequest data) {

        System.out.println("entring the services layer");
        Register register=data.getUserdata();
        boolean validotp=otpService.verifyOtp(register.getEmail(),data.getOtp());
        System.out.println("otp status"+validotp);
        if(!validotp)
        {
            return ResponseEntity.badRequest().body("invalid otp or expired");


        }



        User user=new User();
        user.setUsername(register.getUser_name());
        user.setPhonenumber(register.getPhone_number());
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setRole("ROLE_" + register.getRole().toUpperCase());
        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole());
        }

        user.setPassword(passwordEncoder.encode(register.getPassword()));
        if (sendmailforsucessfullregistery(register.getEmail())) {
            repo.save(user);
            return ResponseEntity.ok("user Register Sucessfully");
        }
        return ResponseEntity.badRequest().body("not able to register the user");


    }



    public ResponseEntity<?> loginuser(Login request)      // this login is comming from the dto folder
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("inavlid username,email,password");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Somethinks Went wrong please try again after some time" + e.getMessage());
        }
        //load user form db
        User user = repo.findByEmail(request.getEmail()).orElseThrow();
        //jwt acess token
        String jwtacesstoken = jwt.GenerateAcessToken(user.getEmail(), user.getRole());
        // jwt refresh token
        String refreshtoken = createrefreshtoken(user);
        return ResponseEntity.ok(new AuthenticatedResponce(jwtacesstoken, refreshtoken, user.getRole(), user.getEmail()));
// login method need to be completed
    }


    // imortant methods
    public ResponseEntity<?> refreshtoken(Refreshtokenrequset request) {
        Refreshtoken refresh = refreshtokenRepository.findByToken(request.getRefreshToken()).orElse(null);
        if (refresh == null) {
            return ResponseEntity.status(401).body("Invalid Refresh Token");
        }
        if (refresh.getExpiryDate().isBefore(Instant.now())) {
            refreshtokenRepository.delete(refresh);
            return ResponseEntity.status(401).body("Refresh Token Expired please login again");
        }
        User user = refresh.getUser();
        String newacesstoken = jwt.GenerateAcessToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new AuthenticatedResponce(newacesstoken, request.getRefreshToken(), user.getRole(), user.getEmail()));
    }

    private String createrefreshtoken(User user) {
        refreshtokenRepository.deleteByUser(user);
        Refreshtoken token = new Refreshtoken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        //token.setExpirydate(new java.util.Date(System.currentTimeMillis()+refteshtokenexpiry));
        token.setExpiryDate(Instant.now().plusMillis(refteshtokenexpiry));
        refreshtokenRepository.save(token);
        return token.getToken();
    }

    // forget password method
    public ResponseEntity<?> forgetpassword_method(forgotpassword request) {
        System.out.println("entering the  forgot password services layer data="+request);
        User user = repo.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            System.out.print("email does not exits to our database");
            return ResponseEntity.ok("if email registerd then reset link will we send");
        }
        String ResetToken = UUID.randomUUID().toString();
        System.out.print("token is for password reset is "+ResetToken);
        user.setResetToken(ResetToken);
        user.setResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(15));
        sendResetEmail(request.getEmail(),ResetToken);
        repo.save(user);
        // till now email is not being send becasue it is in the testing phase and development phase
        return ResponseEntity.ok("If this email exists, a reset link has been sent");


    }

    // RESET PASSWORD METHOD
    public ResponseEntity<?> restpassword_method(resetpassword request) {
        User user = repo.findByResetToken(request.getToken()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid reset token");
        }
        if (user.getResetTokenExpiry() != null && user.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Reset_token got expired");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetTokenExpiry(null);
        user.setResetToken(null);
        repo.save(user);
        return ResponseEntity.ok("Password Reset Sucessfully");
    }

    public ResponseEntity<?> logout_Method(String email) {
        User user = repo.findByEmail(email).orElse(null);
        ;
        if (user != null) {
            refreshtokenRepository.deleteByUser(user);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

     private void sendResetEmail(String toEmail, String resetToken) {
        String resetLink = frontendurl + "/reset-password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText(
            "Hello,\n\n" +
            "You requested to reset your password.\n\n" +
            "Click the link below to reset it (valid for 15 minutes):\n" +
            resetLink + "\n\n" +
            "If you did not request this, please ignore this email.\n\n" +
            "Developer Portfolio Team"
        );
        mailSender.send(message);
    }
    private boolean sendmailforsucessfullregistery(String mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail);
            message.setSubject("you have sucessfully register to our application Developer prtopolio Generator");
            message.setText("Hello,/n/n" +
                    " congurlation you have sucessfully registred to our applicatio " +
                    "/n/n" + "for any  query you can connect to nitishgupta ");
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            System.out.println("message for sedning the mail" + e);
            return false;

        }

}
public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";;
        return password.matches(regex);

    }



}
