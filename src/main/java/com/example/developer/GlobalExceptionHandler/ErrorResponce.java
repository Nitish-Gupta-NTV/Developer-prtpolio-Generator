package com.example.developer.GlobalExceptionHandler;

public class ErrorResponce {
    private int status;
    private String message;

    public ErrorResponce(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
