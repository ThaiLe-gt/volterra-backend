package com.dt.energy.dashboard.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class EnergyDashboardController {
    @GetMapping("/api/v1/energy/dashboard/latest")
    ApiResponse<Map<String, Object>> latest() {
        return ApiResponse.success(Map.of("status", "energy dashboard scaffold"));
    }
}
