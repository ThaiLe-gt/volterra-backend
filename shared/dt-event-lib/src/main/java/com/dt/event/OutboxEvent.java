package com.dt.event;

import java.time.Instant;
import java.util.UUID;

public record OutboxEvent(
        UUID id,
        String topic,
        String eventType,
        String aggregateType,
        String aggregateId,
        String payloadJson,
        OutboxStatus status,
        int retryCount,
        Instant nextRetryAt,
        String lastError,
        Instant createdAt
) {
}
