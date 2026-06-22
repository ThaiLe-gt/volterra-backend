package com.dt.event;

public interface EventPublisher {
    void publish(String topic, EventEnvelope<?> event);
}
