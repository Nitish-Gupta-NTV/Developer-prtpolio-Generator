package com.example.developer.Repository;

import com.example.developer.model.User;
import com.example.developer.model.custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface customRepo extends JpaRepository<custom,Long> {
    List<custom> findByUser(User user);
}
