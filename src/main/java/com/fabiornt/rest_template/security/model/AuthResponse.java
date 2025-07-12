package com.fabiornt.rest_template.security.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for authentication responses.
 * Used to send JWT token and related information to clients.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private Long expiresAt;
    private String username;

    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * Static factory method to create an AuthResponse with a token and expiration.
     * 
     * @param token The JWT token
     * @param expiresAt The expiration time in milliseconds
     * @param username The username of the authenticated user
     * @return A new AuthResponse instance
     */
    public static AuthResponse of(String token, Long expiresAt, String username) {
        return AuthResponse.builder()
                .token(token)
                .expiresAt(expiresAt)
                .username(username)
                .build();
    }
}
