package com.fabiornt.rest_template.http;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response wrapper for paginated collections with HATEOAS links and pagination metadata.
 * 
 * @param <T> Type of the data in the collection
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedApiResponseCollection<T> {
    private List<T> data;
    private Link[] links;
    private HttpStatus status;
    private PageMetadata metadata;
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    /**
     * Creates a PagedApiResponseCollection from a Spring Data Page.
     * 
     * @param <T> Type of the data
     * @param page The Spring Data Page
     * @param links HATEOAS links
     * @param status HTTP status
     * @return PagedApiResponseCollection
     */
    public static <T> PagedApiResponseCollection<T> fromPage(Page<?> page, List<T> data, Link[] links, HttpStatus status) {
        return PagedApiResponseCollection.<T>builder()
                .data(data)
                .links(links)
                .status(status)
                .metadata(PageMetadata.fromPage(page))
                .build();
    }
    
    /**
     * Metadata for pagination.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageMetadata {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean first;
        private boolean last;
        
        /**
         * Creates PageMetadata from a Spring Data Page.
         * 
         * @param page The Spring Data Page
         * @return PageMetadata
         */
        public static PageMetadata fromPage(Page<?> page) {
            return PageMetadata.builder()
                    .page(page.getNumber())
                    .size(page.getSize())
                    .totalElements(page.getTotalElements())
                    .totalPages(page.getTotalPages())
                    .first(page.isFirst())
                    .last(page.isLast())
                    .build();
        }
    }
}
