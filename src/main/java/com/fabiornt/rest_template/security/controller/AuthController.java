package com.fabiornt.rest_template.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabiornt.rest_template.domain.entity.User;
import com.fabiornt.rest_template.http.ApiResponse;
import com.fabiornt.rest_template.http.ResponseBuilder;
import com.fabiornt.rest_template.security.jwt.JwtTokenProvider;
import com.fabiornt.rest_template.security.model.AuthRequest;
import com.fabiornt.rest_template.security.model.AuthResponse;
import com.fabiornt.rest_template.security.model.Role;
import com.fabiornt.rest_template.security.model.UserPrincipal;
import com.fabiornt.rest_template.service.UserService;

import jakarta.validation.Valid;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * Authenticate user and generate JWT token.
     *
     * @param loginRequest The login request
     * @return JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Get expiration date
        long expiresAt = tokenProvider.getExpirationDateFromToken(jwt).getTime();

        // Get username
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        // Create response
        AuthResponse authResponse = AuthResponse.of(jwt, expiresAt, username);

        return ResponseBuilder.success(authResponse);
    }

    /**
     * Register a new user.
     *
     * @param user The user to register
     * @return The registered user
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody User user) {
        // Store the raw password before it gets encoded
        String rawPassword = user.getPassword();

        // Create user with default role
        user.addRole(Role.ROLE_USER);
        User createdUser = userService.createUser(user);

        // Authenticate the new user with the raw password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword));

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Get expiration date
        long expiresAt = tokenProvider.getExpirationDateFromToken(jwt).getTime();

        // Create response
        AuthResponse authResponse = AuthResponse.of(jwt, expiresAt, createdUser.getUsername());

        return ResponseBuilder.created(authResponse);
    }
}
