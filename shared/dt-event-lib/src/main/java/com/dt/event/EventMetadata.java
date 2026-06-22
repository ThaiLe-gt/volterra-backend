package com.dt.event;

import java.time.Instant;
import java.util.UUID;

public record EventMetadata(
        UUID eventId,
        String eventType,
        String aggregateType,
        String aggregateId,
        String tenantId,
        String siteId,
        String correlationId,
        String causationId,
        Instant occurredAt,
        int schemaVersion
) {
    public EventMetadata {
        if (eventId == null) {
            throw new IllegalArgumentException("eventId is required");
        }
        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("eventType is required");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("occurredAt is required");
        }
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be positive");
        }
    }
}
