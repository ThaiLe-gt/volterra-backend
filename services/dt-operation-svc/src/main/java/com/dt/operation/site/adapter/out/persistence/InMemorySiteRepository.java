package com.dt.operation.site.adapter.out.persistence;

import com.dt.operation.site.domain.Site;
import com.dt.operation.site.domain.SiteId;
import com.dt.operation.site.domain.SiteRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class InMemorySiteRepository implements SiteRepository {
    private final ConcurrentHashMap<SiteId, Site> sites = new ConcurrentHashMap<>();

    @Override
    public Site save(Site site) {
        sites.put(site.id(), site);
        return site;
    }

    @Override
    public Optional<Site> findById(SiteId id) {
        return Optional.ofNullable(sites.get(id));
    }

    @Override
    public List<Site> findAll() {
        return new ArrayList<>(sites.values());
    }
}
