package com.dt.common.api;

import java.time.Instant;

public record ApiResponse<T>(
        boolean succeeded,
        String message,
        T data,
        Instant timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "success", data, Instant.now());
    }

    public static <T> ApiResponse<T> accepted(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

    public static ApiResponse<Void> success() {
        return success(null);
    }

    public static ApiResponse<Void> failure(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}
