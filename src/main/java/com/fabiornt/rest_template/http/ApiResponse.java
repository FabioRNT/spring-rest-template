package com.fabiornt.rest_template.http;

import java.time.LocalDateTime;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>
{
    private T data;
    private HttpStatus status;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
