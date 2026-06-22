package com.dt.operation.site.application;

import com.dt.operation.site.domain.Site;
import com.dt.operation.site.domain.SiteId;
import com.dt.operation.site.domain.SiteRepository;

import java.time.Instant;
import java.util.UUID;

public class CreateSiteUseCase {
    private final SiteRepository siteRepository;

    public CreateSiteUseCase(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public Site create(UUID tenantId, String code, String name, String timezone) {
        return siteRepository.save(new Site(
                new SiteId(UUID.randomUUID()),
                tenantId,
                code,
                name,
                timezone,
                "COMMISSIONING",
                Instant.now()
        ));
    }
}
