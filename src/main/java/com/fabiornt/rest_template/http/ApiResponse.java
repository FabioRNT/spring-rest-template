package com.fabiornt.rest_template.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>
{
    private T data;
    private Object metadata;
}
