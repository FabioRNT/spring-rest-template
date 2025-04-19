package com.fabiornt.rest_template.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
