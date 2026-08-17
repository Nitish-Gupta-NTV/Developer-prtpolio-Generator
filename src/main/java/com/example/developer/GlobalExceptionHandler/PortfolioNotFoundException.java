package com.example.developer.GlobalExceptionHandler;

public class PortfolioNotFoundException extends RuntimeException {
    public PortfolioNotFoundException(String message) {
        super(message);
    }


}
