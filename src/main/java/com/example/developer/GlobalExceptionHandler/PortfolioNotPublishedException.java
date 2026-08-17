package com.example.developer.GlobalExceptionHandler;

public class PortfolioNotPublishedException extends RuntimeException{
    public PortfolioNotPublishedException(String message)
    {
        super(message);
    }
}
