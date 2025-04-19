package com.fabiornt.rest_template.http;

import java.time.LocalDateTime;
import java.util.List;

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
public class ApiResponseCollection<T>
{
    private List<T> data;
    private Link[] links;
    private HttpStatus status;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
