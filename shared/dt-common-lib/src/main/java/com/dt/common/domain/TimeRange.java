package com.dt.common.domain;

import java.time.Instant;

public record TimeRange(Instant start, Instant end) {
    public TimeRange {
        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end are required");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end must not be before start");
        }
    }
}
