package com.dt.operation.asset.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class AssetController {
    @GetMapping("/api/v1/assets")
    ApiResponse<List<Map<String, Object>>> listAssets() {
        return ApiResponse.success(List.of());
    }

    @PostMapping("/api/v1/assets")
    ApiResponse<Map<String, Object>> createAsset(@RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("asset registry scaffold", request);
    }
}
