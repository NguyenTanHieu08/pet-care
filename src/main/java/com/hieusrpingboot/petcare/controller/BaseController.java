package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    /**
     * Creates a successful response with data
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    /**
     * Creates a successful response without data
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(String message) {
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    /**
     * Creates a successful response with custom status code
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(String message, T data, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.success(message, data));
    }

    /**
     * Creates an error response with custom status code
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(message, status.value()));
    }

    /**
     * Creates an error response with BAD_REQUEST status
     */
    protected <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates an error response with NOT_FOUND status
     */
    protected <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return error(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates an error response with CONFLICT status
     */
    protected <T> ResponseEntity<ApiResponse<T>> conflict(String message) {
        return error(message, HttpStatus.CONFLICT);
    }

    /**
     * Creates an error response with UNAUTHORIZED status
     */
    protected <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        return error(message, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Creates an error response with INTERNAL_SERVER_ERROR status
     */
    protected <T> ResponseEntity<ApiResponse<T>> internalServerError(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Validates if ID is valid (not null and greater than 0)
     */
    protected boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    /**
     * Validates if string is not null or empty
     */
    protected boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
