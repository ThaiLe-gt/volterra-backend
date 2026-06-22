package com.dt.alarm.adapter.in.web;

import com.dt.common.api.ApiResponse;
import com.dt.event.EventFactory;
import com.dt.event.EventPublisher;
import com.dt.event.EventTopicNames;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class AlarmController {
    private final EventPublisher eventPublisher;

    AlarmController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/api/v1/alarm-rules")
    ApiResponse<List<Map<String, Object>>> rules() {
        return ApiResponse.success(List.of());
    }

    @PostMapping("/api/v1/alarm-rules")
    ApiResponse<Map<String, Object>> createRule(@RequestBody Map<String, Object> request) {
        return ApiResponse.accepted("alarm rule scaffold", request);
    }

    @GetMapping("/api/v1/alarm-events")
    ApiResponse<List<Map<String, Object>>> events() {
        return ApiResponse.success(List.of());
    }

    @PostMapping("/api/v1/alarm-events/{eventId}/acknowledge")
    ApiResponse<Map<String, Object>> acknowledge(@PathVariable String eventId) {
        eventPublisher.publish(EventTopicNames.ALARM_EVENTS, EventFactory.create(
                "AlarmAcknowledged", "AlarmEvent", eventId, null, null, null, null, Map.of("eventId", eventId)
        ));
        return ApiResponse.accepted("alarm acknowledge scaffold", Map.of("eventId", eventId));
    }
}
