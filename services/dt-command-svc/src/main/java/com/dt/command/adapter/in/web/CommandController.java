package com.dt.command.adapter.in.web;

import com.dt.common.api.ApiResponse;
import com.dt.event.EventFactory;
import com.dt.event.EventPublisher;
import com.dt.event.EventTopicNames;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
class CommandController {
    private final EventPublisher eventPublisher;

    CommandController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/api/v1/commands")
    ApiResponse<Map<String, Object>> submit(@RequestBody Map<String, Object> request) {
        var commandId = UUID.randomUUID();
        eventPublisher.publish(EventTopicNames.COMMAND_EVENTS, EventFactory.create(
                "CommandRequested",
                "Command",
                commandId.toString(),
                stringOrNull(request.get("tenantId")),
                stringOrNull(request.get("siteId")),
                stringOrNull(request.get("correlationId")),
                null,
                request
        ));
        return ApiResponse.accepted("command accepted scaffold", Map.of(
                "commandId", commandId,
                "status", "REQUESTED",
                "requestedAt", Instant.now(),
                "request", request
        ));
    }

    @GetMapping("/api/v1/commands/{commandId}")
    ApiResponse<Map<String, Object>> get(@PathVariable String commandId) {
        return ApiResponse.success(Map.of("commandId", commandId, "status", "SCAFFOLD"));
    }

    @PostMapping("/api/v1/commands/{commandId}/approve")
    ApiResponse<Map<String, Object>> approve(@PathVariable String commandId) {
        eventPublisher.publish(EventTopicNames.COMMAND_EVENTS, EventFactory.create(
                "CommandApproved", "Command", commandId, null, null, null, null, Map.of("commandId", commandId)
        ));
        return ApiResponse.accepted("command approval scaffold", Map.of("commandId", commandId, "status", "APPROVED"));
    }

    @PostMapping("/api/v1/commands/{commandId}/cancel")
    ApiResponse<Map<String, Object>> cancel(@PathVariable String commandId) {
        eventPublisher.publish(EventTopicNames.COMMAND_EVENTS, EventFactory.create(
                "CommandCancelled", "Command", commandId, null, null, null, null, Map.of("commandId", commandId)
        ));
        return ApiResponse.accepted("command cancel scaffold", Map.of("commandId", commandId, "status", "CANCELLED"));
    }

    @GetMapping({"/signal/control-grid", "/signal/control-solar", "/signal/control-bess", "/signal/control-charge-discharge"})
    ApiResponse<Map<String, Object>> controlConfig() {
        return ApiResponse.success(Map.of("status", "legacy control config scaffold"));
    }

    @PostMapping({"/signal/control-grid", "/signal/control-solar", "/signal/control-bess", "/signal/control-charge-discharge"})
    ApiResponse<Map<String, Object>> legacyControl(@RequestBody Map<String, Object> request) {
        return submit(request);
    }

    private static String stringOrNull(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
