package com.example.developer.GlobalExceptionHandler;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity badrequesr(RuntimeException runtimeException)
    {
        return ResponseEntity.badRequest().body(Map.of("error",runtimeException.getMessage()));

    }
}
