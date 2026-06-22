package com.dt.event;

import java.time.Instant;

public record PublishedEvent(
        String topic,
        EventEnvelope<?> event,
        Instant publishedAt
) {
}
