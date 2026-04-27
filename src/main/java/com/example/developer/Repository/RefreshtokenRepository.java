package com.example.developer.Repository;

import com.example.developer.model.Refreshtoken;
import com.example.developer.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshtokenRepository extends JpaRepository<Refreshtoken,Long> {

    Optional<Refreshtoken> findByToken(String token);


    @Transactional
    void deleteByUser(User user);
}
