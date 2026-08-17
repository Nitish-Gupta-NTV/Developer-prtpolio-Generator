package com.example.developer.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.ErrorResponse;
import com.example.developer.GlobalExceptionHandler.ErrorResponce;

import java.util.HashMap;
import java.util.Map;
/*
@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity badrequesr(RuntimeException runtimeException)
    {
        return ResponseEntity.badRequest().body(Map.of("error",runtimeException.getMessage()));

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(SlugAlreadyTakenException.class)
    public ResponseEntity<?> handleSlugTaken(SlugAlreadyTakenException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT) // 409
                .body(HttpStatus.CONFLICT+"Slug already Taken"+ex);
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<?> handlePortfolioNotFound(PortfolioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
                .body(HttpStatus.CONFLICT+"PrtopolioNotfoundException"+ex);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Illegal argument exceptino"+HttpStatus.CONFLICT+ex);
    }
    @ExceptionHandler(PortfolioNotPublishedException.class)
    public ResponseEntity<?> handlePortfolioNotPublishedException(PortfolioNotPublishedException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("portpolionotpublished "+HttpStatus.CONFLICT+ex);
    }



}
*/
@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> badRequest(RuntimeException ex) {

        String message = ex.getMessage();

        if (message == null) {
            message = "An unexpected error occurred";
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(SlugAlreadyTakenException.class)
    public ResponseEntity<?> handleSlugTaken(
            SlugAlreadyTakenException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Slug already taken"
                ));
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<?> handlePortfolioNotFound(
            PortfolioNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Portfolio not found"
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(
            IllegalArgumentException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error",
                        ex.getMessage() != null
                                ? ex.getMessage()
                                : "Invalid argument"
                ));
    }

    @ExceptionHandler(PortfolioNotPublishedException.class)
    public ResponseEntity<?> handlePortfolioNotPublishedException(
            PortfolioNotPublishedException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "Portfolio is not published"
                ));
    }
}