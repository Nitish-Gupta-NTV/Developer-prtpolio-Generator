package com.example.developer.Repository;

import com.example.developer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.developer.model.certaficatios;

import java.util.List;

@Repository
public interface CertaficationsRepo extends JpaRepository<certaficatios,Long> {

    List<certaficatios> findByUser(User user);
}
