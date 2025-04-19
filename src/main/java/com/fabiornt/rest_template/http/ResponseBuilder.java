package com.fabiornt.rest_template.http;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    
    /**
     * Creates a success response with data and links
     * 
     * @param <T> Type of the data
     * @param data The data to include in the response
     * @param links HATEOAS links to include
     * @return ResponseEntity with ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, Link... links) {
        return ResponseEntity.ok(
            ApiResponse.<T>builder()
                .data(data)
                .status(HttpStatus.OK)
                .links(links)
                .build()
        );
    }
    
    /**
     * Creates a success response with data, status, and links
     * 
     * @param <T> Type of the data
     * @param data The data to include in the response
     * @param status HTTP status code
     * @param links HATEOAS links to include
     * @return ResponseEntity with ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, HttpStatus status, Link... links) {
        return ResponseEntity.status(status).body(
            ApiResponse.<T>builder()
                .data(data)
                .status(status)
                .links(links)
                .build()
        );
    }
    
    /**
     * Creates a success response with a collection of data and links
     * 
     * @param <T> Type of the data
     * @param data The collection of data to include in the response
     * @param links HATEOAS links to include
     * @return ResponseEntity with ApiResponseCollection
     */
    public static <T> ResponseEntity<ApiResponseCollection<T>> collection(List<T> data, Link... links) {
        return ResponseEntity.ok(
            ApiResponseCollection.<T>builder()
                .data(data)
                .status(HttpStatus.OK)
                .links(links)
                .build()
        );
    }
    
    /**
     * Creates an error response
     * 
     * @param status HTTP status code
     * @param message Error message
     * @param error Error type
     * @return ResponseEntity with ApiErrorResponse
     */
    public static ResponseEntity<ApiErrorResponse> error(HttpStatus status, String message, String error) {
        return ResponseEntity.status(status).body(
            ApiErrorResponse.builder()
                .status(status)
                .message(message)
                .error(error)
                .build()
        );
    }
    
    /**
     * Creates an error response with details
     * 
     * @param status HTTP status code
     * @param message Error message
     * @param error Error type
     * @param details List of error details
     * @return ResponseEntity with ApiErrorResponse
     */
    public static ResponseEntity<ApiErrorResponse> error(HttpStatus status, String message, String error, List<String> details) {
        return ResponseEntity.status(status).body(
            ApiErrorResponse.builder()
                .status(status)
                .message(message)
                .error(error)
                .details(details)
                .build()
        );
    }
    
    /**
     * Creates a 'created' response with data and links
     * 
     * @param <T> Type of the data
     * @param data The data to include in the response
     * @param links HATEOAS links to include
     * @return ResponseEntity with ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, Link... links) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<T>builder()
                .data(data)
                .status(HttpStatus.CREATED)
                .links(links)
                .build()
        );
    }
    
    /**
     * Creates a 'no content' response
     * 
     * @return ResponseEntity with no content
     */
    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
}
