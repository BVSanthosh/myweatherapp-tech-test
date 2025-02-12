package com.weatherapp.myweatherapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class) 
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class) 
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class) 
    public ResponseEntity<String> handleHttpClientError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class) 
    public ResponseEntity<String> handleHttpServerError(HttpServerErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}