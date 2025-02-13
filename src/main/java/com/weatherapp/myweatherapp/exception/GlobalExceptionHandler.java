package com.weatherapp.myweatherapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Global Exception Handler responsible for catching exceptions across the entire applicaton.
 * This class a appropriate HTTP response wih the appropriate error message when an exception occurs.  
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where an invalid argument is provided.
     * 
     * @param e The exception containing details about the invalid argument.
     * @return A `400 Bad Request` response with the error message.
     */
    @ExceptionHandler(IllegalArgumentException.class) 
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles cases where the CityInfo object is in an invalid state.
     * 
     * @param e The exception containing details about the invalid state.
     * @return A `500 Internal Server Error` response with the error message.
     */
    @ExceptionHandler(IllegalStateException.class) 
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    /**
     * Handles client errors due to invalid requests (e.g., invalid city name, invalid API key).
     * 
     * @param e The exception containing details about the client error.
     * @return A response with the error status code and message.
     */
    @ExceptionHandler(HttpClientErrorException.class) 
    public ResponseEntity<String> handleHttpClientError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    /**
     * Handles server errors.
     * 
     * @param e The exception containing details about the server error.
     * @return A response with the error status code and message.
     */
    @ExceptionHandler(HttpServerErrorException.class) 
    public ResponseEntity<String> handleHttpServerError(HttpServerErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    /**
     * Handles all other exceptions.
     * 
     * @param e The exception containing details about the error.
     * @return A `500 Internal Server Error` response with the error message.
     */
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}