package com.fabiornt.rest_template.exception;

/**
 * Exception thrown when attempting to create a user with a username that already exists.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    
    public UsernameAlreadyExistsException(String username) {
        super(String.format("Username already exists: %s", username));
    }
}