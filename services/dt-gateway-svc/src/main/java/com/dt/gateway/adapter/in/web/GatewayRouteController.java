package com.dt.gateway.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
class GatewayRouteController {
    @GetMapping("/api/v1/gateway/routes")
    ApiResponse<Map<String, List<String>>> routes() {
        return ApiResponse.success(Map.of(
                "preferred", List.of("/api/v1/auth", "/api/v1/sites", "/api/v1/telemetry", "/api/v1/commands"),
                "compatibility", List.of("/auth/*", "/station/*", "/signal/*", "/ocpp/*", "/manage/charge/*")
        ));
    }
}
