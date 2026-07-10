package com.example.developer.Service;

import com.example.developer.DTO.AuthenticatedResponce;
import com.example.developer.DTO.Login;
import com.example.developer.DTO.Refreshtokenrequset;
import com.example.developer.DTO.forgotpassword;
import com.example.developer.DTO.resetpassword;
import com.example.developer.JWTSECURITY.jwtUtils;
import com.example.developer.Repository.RefreshtokenRepository;
import com.example.developer.Repository.UserRepoitory;
import com.example.developer.model.Refreshtoken;
import com.example.developer.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sound.midi.SysexMessage;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Userregitery {
private final UserRepoitory repo;
private final RefreshtokenRepository refreshtokenRepository;
private final PasswordEncoder passwordEncoder;
private final jwtUtils jwt;
private  final AuthenticationManager authenticationManager;


@Value("${jwt.refresh-token-expiry}")
private long refteshtokenexpiry;
@Value("${frontend_url}")
private String frontendurl;


public ResponseEntity<?> regsiteruser(User user)
{
    System.out.println("entring the services layer");
   if(repo.existsByUsername(user.getUsername()))
   {
       return ResponseEntity.badRequest().body("UserName is Not available");
   }
   if(repo.existsByEmail(user.getEmail()))
   {
       return ResponseEntity.badRequest().body("email already registerd");
   }
   if(repo.existsByPhonenumber(user.getPhonenumber()))
   {
       return ResponseEntity.badRequest().body("phone number is already exits");
   }
    if(!isValidPassword(user.getPassword())){

        return ResponseEntity.badRequest().body("password is not valid");
    }
    user.setRole("ROLE_" + user.getRole().toUpperCase());
    if (!user.getRole().startsWith("ROLE_")) {
        user.setRole("ROLE_" + user.getRole());
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    repo.save(user);
    return ResponseEntity.ok("user Register Sucessfully");


}
public ResponseEntity<?> loginuser(Login request)      // this login is comming from the dto folder
{
    try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
    } catch (AuthenticationException e) {
        return ResponseEntity.badRequest().body("inavlid username,email,password");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Somethinks Went wrong please try again after some time"+e.getMessage());
    }
    //load user form db
    User user=repo.findByEmail(request.getEmail()).orElseThrow();
    //jwt acess token
    String jwtacesstoken=jwt.GenerateAcessToken(user.getEmail(),user.getRole());
    // jwt refresh token
    String refreshtoken=createrefreshtoken(user);
    return ResponseEntity.ok(new AuthenticatedResponce(jwtacesstoken,refreshtoken,user.getRole(), user.getEmail()));
// login method need to be completed
}



// imortant methods
    public ResponseEntity<?> refreshtoken(Refreshtokenrequset request)
    {
        Refreshtoken refresh= refreshtokenRepository.findByToken(request.getRefreshToken()).orElse(null);
        if(refresh==null)
        {
           return ResponseEntity.status(401).body("Invalid Refresh Token");
        }
        if(refresh.getExpiryDate().isBefore(Instant.now()))
        {
            refreshtokenRepository.delete(refresh);
            return ResponseEntity.status(401).body("Refresh Token Expired please login again");
        }
        User user=refresh.getUser();
        String newacesstoken=jwt.GenerateAcessToken(user.getEmail(),user.getRole());
        return ResponseEntity.ok(new AuthenticatedResponce(newacesstoken,request.getRefreshToken(), user.getRole(), user.getEmail()));
    }

    private String createrefreshtoken(User user)
    {
        refreshtokenRepository.deleteByUser(user);
        Refreshtoken token=new Refreshtoken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        //token.setExpirydate(new java.util.Date(System.currentTimeMillis()+refteshtokenexpiry));
        token.setExpiryDate(Instant.now().plusMillis(refteshtokenexpiry));
        refreshtokenRepository.save(token);
        return token.getToken();
    }
    // forget password method
    public ResponseEntity<?> forgetpassword_method(forgotpassword request)
    {
        User user=repo.findByEmail(request.getEmail()).orElse(null);
        if(user==null)
        {
            System.out.print("email does not exits to our database");
            return ResponseEntity.ok("if email registerd then reset link will we send");
        }
        String ResetToken=UUID.randomUUID().toString();
        user.setResetToken(ResetToken);
        user.setResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(15));
        repo.save(user);
        // till now email is not being send becasue it is in the testing phase and development phase
        return ResponseEntity.ok("If this email exists, a reset link has been sent");


    }
    // RESET PASSWORD METHOD
    public ResponseEntity<?> restpassword_method (resetpassword request)
    {
        User user=repo.findByResetToken(request.getToken()).orElse(null);
        if(user==null)
        {
           return ResponseEntity.badRequest().body("Invalid reset token");
        }
        if(user.getResetTokenExpiry()!=null&&user.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now()))
        {
            return ResponseEntity.badRequest().body("Reset_token got expired");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetTokenExpiry(null);
        user.setResetToken(null);
        repo.save(user);
        return ResponseEntity.ok("Password Reset Sucessfully");
    }
    public ResponseEntity<?> logout_Method(String email)
    {
        User user=repo.findByEmail(email).orElse(null);;
        if(user!=null)
        {
            refreshtokenRepository.deleteByUser(user);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    /* private void sendResetEmail(String toEmail, String resetToken) {
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
    }*/
























    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";;
        return password.matches(regex);

    }


}
