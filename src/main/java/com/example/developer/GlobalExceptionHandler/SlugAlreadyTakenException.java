package com.example.developer.GlobalExceptionHandler;

public class SlugAlreadyTakenException extends RuntimeException{
    public SlugAlreadyTakenException(String message) {
        super(message);
    }
}
