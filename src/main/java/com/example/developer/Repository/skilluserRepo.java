package com.example.developer.Repository;

import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.developer.model.skilluser;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface skilluserRepo extends JpaRepository<skilluser,Long> {


    List<skilluser> findByUser(User user);
}
