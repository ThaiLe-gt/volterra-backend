package com.dt.security;

import java.util.List;
import java.util.UUID;

public record CurrentUser(
        UUID accountId,
        UUID tenantId,
        List<UUID> roleIds,
        List<UUID> siteScope
) {
}
