package com.dt.auth.account.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
class AuthController {
    @PostMapping({"/api/v1/auth/login", "/auth/login"})
    ApiResponse<Map<String, Object>> login(@RequestBody LoginRequest request) {
        return ApiResponse.accepted("auth service scaffold: replace with OAuth2 token issuing",
                Map.of("username", request.username(), "issuedAt", Instant.now()));
    }

    @PostMapping({"/api/v1/auth/refresh", "/auth/refresh"})
    ApiResponse<Void> refresh() {
        return ApiResponse.accepted("auth refresh scaffold", null);
    }

    @GetMapping({"/api/v1/auth/logout", "/auth/logout"})
    ApiResponse<Void> logout() {
        return ApiResponse.success();
    }

    record LoginRequest(String username, String password) {
    }
}
