package com.dt.operation.site.domain;

import java.util.List;
import java.util.Optional;

public interface SiteRepository {
    Site save(Site site);

    Optional<Site> findById(SiteId id);

    List<Site> findAll();
}
