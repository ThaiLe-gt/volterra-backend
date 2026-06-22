package com.dt.event;

public record EventEnvelope<T>(
        EventMetadata metadata,
        T payload
) {
    public EventEnvelope {
        if (metadata == null) {
            throw new IllegalArgumentException("metadata is required");
        }
    }
}
