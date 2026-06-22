package com.dt.telemetry.adapter.in.web;

import com.dt.common.api.ApiResponse;
import com.dt.event.EventFactory;
import com.dt.event.EventPublisher;
import com.dt.event.EventTopicNames;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
class TelemetryController {
    private final EventPublisher eventPublisher;

    TelemetryController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping({"/api/v1/telemetry/latest", "/signal/latest"})
    ApiResponse<Map<String, Object>> latest(@RequestParam(required = false) String siteId) {
        return ApiResponse.success(Map.of("siteId", siteId == null ? "" : siteId, "latestTime", Instant.now()));
    }

    @GetMapping({"/api/v1/telemetry/latest-raw", "/signal/latest-raw"})
    ApiResponse<List<Map<String, Object>>> latestRaw() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/telemetry/history", "/signal/historical-list"})
    ApiResponse<List<Map<String, Object>>> history() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({
            "/api/v1/telemetry/history/system", "/signal/list-system-energy",
            "/api/v1/telemetry/history/grid", "/signal/list-grid",
            "/api/v1/telemetry/history/wind", "/signal/list-wind",
            "/api/v1/telemetry/history/solar", "/signal/list-solar",
            "/api/v1/telemetry/history/bess", "/signal/list-bess",
            "/api/v1/telemetry/history/charger", "/signal/list-charger",
            "/api/v1/telemetry/history/weather", "/signal/list-weather"
    })
    ApiResponse<List<Map<String, Object>>> typedHistory() {
        return ApiResponse.success(List.of());
    }

    @PostMapping("/api/v1/telemetry/ingest")
    ApiResponse<Map<String, Object>> ingest(@RequestBody Map<String, Object> event) {
        eventPublisher.publish(EventTopicNames.TELEMETRY_RAW_EVENTS, EventFactory.create(
                "TelemetryRawReceived",
                "Telemetry",
                String.valueOf(event.getOrDefault("dataPointId", "")),
                stringOrNull(event.get("tenantId")),
                stringOrNull(event.get("siteId")),
                stringOrNull(event.get("correlationId")),
                null,
                event
        ));
        return ApiResponse.accepted("telemetry ingest scaffold", event);
    }

    private static String stringOrNull(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
