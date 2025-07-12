package com.fabiornt.rest_template.security.model;

/**
 * Enum representing user roles in the system.
 */
public enum Role {
    ROLE_USER,
    ROLE_ADMIN;
    
    /**
     * Get the authority name for Spring Security.
     * 
     * @return The authority name
     */
    public String getAuthority() {
        return name();
    }
}