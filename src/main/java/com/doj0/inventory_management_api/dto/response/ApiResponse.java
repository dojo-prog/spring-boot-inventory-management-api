package com.doj0.inventory_management_api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude (JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(String message, T data) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(null, data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(message, data);
    }

    public static ApiResponse<Void> message(String message) {
        return new ApiResponse<>(message, null);
    }
}
