package com.example.developer.Repository;

import com.example.developer.model.theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ThemeRepo extends JpaRepository<theme ,Long > {

}
