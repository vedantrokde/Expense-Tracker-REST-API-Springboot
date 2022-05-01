package com.code.et.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EtExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EtAuthException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EtNotFoundException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
