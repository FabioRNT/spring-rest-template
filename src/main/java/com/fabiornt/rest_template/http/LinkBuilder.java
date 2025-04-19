package com.fabiornt.rest_template.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;

import com.fabiornt.rest_template.controller.UserController;

/**
 * Utility class for building HATEOAS links for API responses.
 * Centralizes link creation logic for consistency across the API.
 */
public class LinkBuilder {

    /**
     * Creates standard links for a user resource
     * 
     * @param userId The user ID
     * @return Array of links for the user resource
     */
    public static Link[] forUser(Long userId) {
        return new Link[] {
            linkTo(methodOn(UserController.class).getUserById(userId)).withSelfRel(),
            linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"),
        };
    }
    
    /**
     * Creates standard links for the users collection
     * 
     * @return Array of links for the users collection
     */
    public static Link[] forUsers() {
        return new Link[] {
            linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel(),
            linkTo(methodOn(UserController.class).createUser(null)).withRel("create")
        };
    }
    
    /**
     * Creates a documentation link for the API
     * 
     * @return Link to API documentation
     */
    public static Link docs() {
        return Link.of("/api/docs").withRel("documentation");
    }
    
    /**
     * Creates a link to the API root
     * 
     * @return Link to API root
     */
    public static Link root() {
        return Link.of("/api").withRel("api");
    }
    
    /**
     * Creates a custom link with the given href and relation
     * 
     * @param href The link URL
     * @param rel The link relation
     * @return Custom link
     */
    public static Link custom(String href, String rel) {
        return Link.of(href, LinkRelation.of(rel));
    }
}
