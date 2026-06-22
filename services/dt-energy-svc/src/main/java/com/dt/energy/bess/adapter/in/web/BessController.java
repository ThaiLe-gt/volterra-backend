package com.dt.energy.bess.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class BessController {
    @GetMapping("/api/v1/bess/status")
    ApiResponse<Map<String, Object>> status() {
        return ApiResponse.success(Map.of("status", "bess status scaffold"));
    }

    @GetMapping("/api/v1/bess/schedules")
    ApiResponse<Map<String, Object>> schedules() {
        return ApiResponse.success(Map.of("status", "bess schedule scaffold"));
    }
}
