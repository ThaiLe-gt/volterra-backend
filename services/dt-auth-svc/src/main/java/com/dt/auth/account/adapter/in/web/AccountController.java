package com.dt.auth.account.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class AccountController {
    @GetMapping({"/api/v1/accounts", "/manage/account"})
    ApiResponse<List<Map<String, Object>>> listAccounts() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/profile", "/profile"})
    ApiResponse<Map<String, Object>> profile() {
        return ApiResponse.success(Map.of("status", "profile scaffold"));
    }

    @PutMapping({"/api/v1/profile", "/profile/update"})
    ApiResponse<Void> updateProfile(@RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("profile update scaffold", null);
    }

    @PutMapping({"/api/v1/profile/password", "/profile/password"})
    ApiResponse<Void> changePassword(@RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("password change scaffold", null);
    }
}
