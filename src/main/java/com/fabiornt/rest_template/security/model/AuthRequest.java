package com.fabiornt.rest_template.security.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for authentication requests.
 * Used to receive login credentials from clients.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    
    @NotBlank(message = "Username or email is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}