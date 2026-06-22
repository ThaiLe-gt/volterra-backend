package com.dt.auth.permission.adapter.in.web;

import com.dt.common.api.ApiResponse;
import com.dt.permission.PermissionOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
class PermissionController {
    @GetMapping("/api/v1/permissions/operations")
    ApiResponse<List<PermissionOperation>> operations() {
        return ApiResponse.success(Arrays.asList(PermissionOperation.values()));
    }

    @GetMapping("/api/v1/data-permissions")
    ApiResponse<List<String>> dataPermissions() {
        return ApiResponse.success(List.of("TENANT", "SITE", "LOCATION", "ASSET", "DEVICE"));
    }
}
