package com.example.demo.common.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResponseStatusException exception) {

        var reason = exception.getReason();
        var statusCode = exception.getBody().getStatus();
        var errorResponse = new ErrorResponse(reason, statusCode);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
