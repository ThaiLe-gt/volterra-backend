package com.dt.event;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RecordingEventPublisher implements EventPublisher {
    private final CopyOnWriteArrayList<PublishedEvent> publishedEvents = new CopyOnWriteArrayList<>();

    @Override
    public void publish(String topic, EventEnvelope<?> event) {
        publishedEvents.add(new PublishedEvent(topic, event, Instant.now()));
    }

    public List<PublishedEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
