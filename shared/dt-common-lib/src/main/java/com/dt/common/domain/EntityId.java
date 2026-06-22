package com.dt.common.domain;

import java.util.UUID;

public record EntityId(UUID value) {
    public EntityId {
        if (value == null) {
            throw new IllegalArgumentException("id value is required");
        }
    }

    public static EntityId newId() {
        return new EntityId(UUID.randomUUID());
    }

    public static EntityId from(String value) {
        return new EntityId(UUID.fromString(value));
    }
}
