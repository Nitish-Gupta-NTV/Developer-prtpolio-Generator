package com.example.developer.Repository;

import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoitory extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
     Optional<User>findByUsername(String username);
    boolean existsByEmail(String email);
 //   boolean exitsByPhonenumber(Long number);
 boolean existsByPhonenumber(Long phonenumber);
    boolean existsByUsername(String username);


}
