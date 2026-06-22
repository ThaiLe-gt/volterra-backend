package com.dt.operation.commissioning.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
class CommissioningController {
    @PostMapping("/api/v1/sites/{siteId}/commissioning")
    ApiResponse<Map<String, Object>> startCommissioning(@PathVariable String siteId, @RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("site commissioning scaffold", Map.of("siteId", siteId, "request", request));
    }

    @GetMapping("/api/v1/sites/{siteId}/iot-configs")
    ApiResponse<Map<String, Object>> getIotConfig(@PathVariable String siteId) {
        return ApiResponse.success(Map.of("siteId", siteId, "status", "iot config scaffold"));
    }
}
