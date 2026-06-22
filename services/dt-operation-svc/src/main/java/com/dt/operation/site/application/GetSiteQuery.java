package com.dt.operation.site.application;

import com.dt.operation.site.domain.Site;
import com.dt.operation.site.domain.SiteRepository;

import java.util.List;

public class GetSiteQuery {
    private final SiteRepository siteRepository;

    public GetSiteQuery(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public List<Site> list() {
        return siteRepository.findAll();
    }
}
