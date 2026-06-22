package com.dt.legacy.adapter.in.web;

import com.dt.common.api.ApiResponse;
import com.dt.event.EventFactory;
import com.dt.event.EventPublisher;
import com.dt.event.EventTopicNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
class LegacyDotnetController {
    private final String baseUrl;
    private final EventPublisher eventPublisher;

    LegacyDotnetController(
            @Value("${legacy.dotnet.base-url:http://localhost:8088}") String baseUrl,
            EventPublisher eventPublisher
    ) {
        this.baseUrl = baseUrl;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/api/v1/legacy-dotnet/status")
    ApiResponse<Map<String, Object>> status() {
        return ApiResponse.success(Map.of("baseUrl", baseUrl, "role", "anti-corruption layer scaffold"));
    }

    @PostMapping({
            "/ocpp/start-transaction/{chargePointId}",
            "/ocpp/stop-transaction/{chargePointId}",
            "/ocpp/unlock-connector/{chargePointId}",
            "/ocpp/reserve-now/{chargePointId}",
            "/ocpp/cancel-reservation/{chargePointId}",
            "/ocpp/get-composite-schedule/{chargePointId}",
            "/ocpp/get-configuration/{chargePointId}",
            "/ocpp/change-configuration/{chargePointId}",
            "/ocpp/set-charging-profile/{chargePointId}",
            "/ocpp/clear-charging-profile/{chargePointId}",
            "/ocpp/get-diagnostics/{chargePointId}",
            "/ocpp/reset/{chargePointId}",
            "/ocpp/clear-cache/{chargePointId}",
            "/ocpp/change-availability/{chargePointId}",
            "/ocpp/trigger-message/{chargePointId}",
            "/ocpp/data-transfer/{chargePointId}",
            "/ocpp/get-local-list-version/{chargePointId}",
            "/ocpp/send-local-list/{chargePointId}",
            "/ocpp/update-firmware/{chargePointId}"
    })
    ApiResponse<Map<String, Object>> ocppCommand(@PathVariable String chargePointId, @RequestBody(required = false) Map<String, Object> body) {
        var payload = Map.of("chargePointId", chargePointId, "body", body == null ? Map.of() : body);
        eventPublisher.publish(EventTopicNames.OCPP_EVENTS, EventFactory.create(
                "OcppCommandDelegated", "ChargePoint", chargePointId, null, null, null, null, payload
        ));
        return ApiResponse.accepted("legacy OCPP delegation scaffold", payload);
    }
}
