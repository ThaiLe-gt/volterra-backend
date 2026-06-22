package com.dt.operation.device.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class DeviceController {
    @GetMapping({"/api/v1/devices", "/station/{siteId}/devices"})
    ApiResponse<List<Map<String, Object>>> listDevices(@PathVariable(required = false) String siteId) {
        return ApiResponse.success(List.of());
    }

    @PostMapping({"/api/v1/devices", "/station/{siteId}/device"})
    ApiResponse<Map<String, Object>> createDevice(@RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("device registry scaffold", request);
    }
}
