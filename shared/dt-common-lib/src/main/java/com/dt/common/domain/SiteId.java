package com.dt.common.domain;

import java.util.UUID;

public record SiteId(UUID value) {
    public static SiteId from(String value) {
        return new SiteId(UUID.fromString(value));
    }
}
