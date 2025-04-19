package com.fabiornt.rest_template.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fabiornt.rest_template.http.ApiErrorResponse;
import com.fabiornt.rest_template.http.ResponseBuilder;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseBuilder.error(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            "Email already exists"
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseBuilder.error(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            "Resource not found"
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            details.add(fieldName + ": " + errorMessage);
        });

        return ResponseBuilder.error(
            HttpStatus.BAD_REQUEST,
            "Validation failed",
            "Bad Request",
            details
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseBuilder.error(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            "Internal Server Error"
        );
    }
}
