package com.dt.common.domain;

import java.util.UUID;

public record TenantId(UUID value) {
    public static TenantId from(String value) {
        return new TenantId(UUID.fromString(value));
    }
}
