package com.dt.operation.site.domain;

import java.time.Instant;
import java.util.UUID;

public record Site(
        SiteId id,
        UUID tenantId,
        String code,
        String name,
        String timezone,
        String status,
        Instant createdAt
) {
}
