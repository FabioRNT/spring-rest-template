package com.fabiornt.rest_template.http;

import java.util.List;

import org.springframework.data.domain.Page;
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
                .build()
        );
    }

    /**
     * Creates a success response with data only (no additional links)
     * Used when the data model already contains links
     *
     * @param <T> Type of the data
     * @param data The data to include in the response
     * @return ResponseEntity with ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(
            ApiResponse.<T>builder()
                .data(data)
                .status(HttpStatus.OK)
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
     * Creates a success response with a collection of data only (no additional links)
     * Used when the data models already contain links
     *
     * @param <T> Type of the data
     * @param data The collection of data to include in the response
     * @return ResponseEntity with ApiResponseCollection
     */
    public static <T> ResponseEntity<ApiResponseCollection<T>> collection(List<T> data) {
        return ResponseEntity.ok(
            ApiResponseCollection.<T>builder()
                .data(data)
                .status(HttpStatus.OK)
                .build()
        );
    }

    /**
     * Creates a paginated response with data and links
     *
     * @param <T> Type of the data
     * @param page The Spring Data Page
     * @param data The collection of data to include in the response
     * @param links HATEOAS links to include
     * @return ResponseEntity with PagedApiResponseCollection
     */
    public static <T> ResponseEntity<PagedApiResponseCollection<T>> pagedCollection(Page<?> page, List<T> data, Link... links) {
        return ResponseEntity.ok(
            PagedApiResponseCollection.fromPage(page, data, links, HttpStatus.OK)
        );
    }

    /**
     * Creates a paginated response with data only (no additional links)
     * Used when the data models already contain links
     *
     * @param <T> Type of the data
     * @param page The Spring Data Page
     * @param data The collection of data to include in the response
     * @return ResponseEntity with PagedApiResponseCollection
     */
    public static <T> ResponseEntity<PagedApiResponseCollection<T>> pagedCollection(Page<?> page, List<T> data) {
        return ResponseEntity.ok(
            PagedApiResponseCollection.fromPage(page, data, new Link[0], HttpStatus.OK)
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
     * Creates a 'created' response with data only (no additional links)
     * Used when the data model already contains links
     *
     * @param <T> Type of the data
     * @param data The data to include in the response
     * @return ResponseEntity with ApiResponse
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<T>builder()
                .data(data)
                .status(HttpStatus.CREATED)
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
