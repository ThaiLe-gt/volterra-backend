package com.dt.event;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventHistoryController {
    private final RecordingEventPublisher eventPublisher;

    public EventHistoryController(RecordingEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/api/v1/events/published")
    ApiResponse<List<PublishedEvent>> publishedEvents() {
        return ApiResponse.success(eventPublisher.publishedEvents());
    }
}
