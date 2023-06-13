package com.simpletask.exception.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        errors = Arrays.asList(error);
    }
}