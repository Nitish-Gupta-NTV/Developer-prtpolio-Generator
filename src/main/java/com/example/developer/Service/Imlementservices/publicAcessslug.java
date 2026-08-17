package com.example.developer.Service.Imlementservices;

import com.example.developer.GlobalExceptionHandler.PortfolioNotFoundException;
import com.example.developer.GlobalExceptionHandler.PortfolioNotPublishedException;
import com.example.developer.Repository.PortfolioRepo;
import com.example.developer.model.Portfolio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class publicAcessslug {

    private final PortfolioRepo protRepo;

    public ResponseEntity<?> publicprtopolio(String Slug ){
        Portfolio portfolio=protRepo.findBySlug(Slug).orElseThrow(()->new PortfolioNotFoundException("prtopolio not found exception"));
        if(!portfolio.is_published())
        {
            throw new PortfolioNotPublishedException("prtopolio not published Please try again");

        }
        return ResponseEntity.ok(portfolio);

    }
}
