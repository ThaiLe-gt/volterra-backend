package com.dt.event;

import java.time.Instant;
import java.util.UUID;

public final class EventFactory {
    private EventFactory() {
    }

    public static <T> EventEnvelope<T> create(
            String eventType,
            String aggregateType,
            String aggregateId,
            String tenantId,
            String siteId,
            String correlationId,
            String causationId,
            T payload
    ) {
        return new EventEnvelope<>(
                new EventMetadata(
                        UUID.randomUUID(),
                        eventType,
                        aggregateType,
                        aggregateId,
                        tenantId,
                        siteId,
                        correlationId,
                        causationId,
                        Instant.now(),
                        1
                ),
                payload
        );
    }
}
