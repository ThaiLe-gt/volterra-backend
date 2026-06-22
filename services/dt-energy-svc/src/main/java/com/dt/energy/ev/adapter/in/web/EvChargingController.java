package com.dt.energy.ev.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class EvChargingController {
    @GetMapping({"/api/v1/ev/charge-points", "/manage/charge/list-charger-point"})
    ApiResponse<List<Map<String, Object>>> chargePoints() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/ev/charge-tags", "/manage/charge/list-charger-tag"})
    ApiResponse<List<Map<String, Object>>> chargeTags() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/ev/transactions", "/manage/charge/list-charger-transaction"})
    ApiResponse<List<Map<String, Object>>> transactions() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/ev/active-transactions", "/manage/charge/list-active-transaction"})
    ApiResponse<List<Map<String, Object>>> activeTransactions() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/ev/charge-points/{chargePointId}/connectors", "/station/{siteId}/charger-connectors"})
    ApiResponse<List<Map<String, Object>>> connectors() {
        return ApiResponse.success(List.of());
    }
}
