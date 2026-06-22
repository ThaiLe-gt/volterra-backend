package com.dt.operation.datapoint.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class DataPointController {
    @GetMapping({"/api/v1/data-points", "/station/{siteId}/device-{deviceId}/signals"})
    ApiResponse<List<Map<String, Object>>> listDataPoints() {
        return ApiResponse.success(List.of());
    }

    @PostMapping({"/api/v1/data-points", "/station/{siteId}/device-{deviceId}/signal"})
    ApiResponse<Map<String, Object>> createDataPoint(@RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("data point registry scaffold", request);
    }
}
