package com.dt.operation.site.adapter.in.web;

import com.dt.common.api.ApiResponse;
import com.dt.event.EventFactory;
import com.dt.event.EventPublisher;
import com.dt.event.EventTopicNames;
import com.dt.operation.site.application.CreateSiteUseCase;
import com.dt.operation.site.application.GetSiteQuery;
import com.dt.operation.site.domain.Site;
import com.dt.operation.site.domain.SiteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
class SiteController {
    private final CreateSiteUseCase createSiteUseCase;
    private final GetSiteQuery getSiteQuery;
    private final EventPublisher eventPublisher;

    SiteController(SiteRepository siteRepository, EventPublisher eventPublisher) {
        this.createSiteUseCase = new CreateSiteUseCase(siteRepository);
        this.getSiteQuery = new GetSiteQuery(siteRepository);
        this.eventPublisher = eventPublisher;
    }

    @GetMapping({"/api/v1/sites", "/station/list"})
    ApiResponse<List<Site>> listSites() {
        return ApiResponse.success(getSiteQuery.list());
    }

    @PostMapping({"/api/v1/sites", "/station"})
    ApiResponse<Site> createSite(@RequestBody CreateSiteRequest request) {
        var site = createSiteUseCase.create(request.tenantId(), request.code(), request.name(), request.timezone());
        eventPublisher.publish(EventTopicNames.OPERATION_SITE_EVENTS, EventFactory.create(
                "SiteCreated",
                "Site",
                site.id().value().toString(),
                site.tenantId() == null ? null : site.tenantId().toString(),
                site.id().value().toString(),
                null,
                null,
                site
        ));
        return ApiResponse.success(site);
    }

    record CreateSiteRequest(UUID tenantId, String code, String name, String timezone) {
    }
}
