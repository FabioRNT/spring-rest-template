package com.fabiornt.rest_template.http;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private HttpStatus status;
    private String message;
    private String error;
    @Builder.Default
    private List<String> details = new ArrayList<>();
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
