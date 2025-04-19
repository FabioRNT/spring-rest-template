package com.fabiornt.rest_template.http;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseCollection<T>
{
    private List<T> data;
    private Object metadata;
}
