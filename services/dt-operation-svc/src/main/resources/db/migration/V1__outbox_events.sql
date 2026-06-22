CREATE TABLE IF NOT EXISTS outbox_events (
    id UUID PRIMARY KEY,
    topic VARCHAR(200) NOT NULL,
    event_type VARCHAR(200) NOT NULL,
    aggregate_type VARCHAR(100),
    aggregate_id VARCHAR(100),
    payload_json TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    retry_count INTEGER NOT NULL DEFAULT 0,
    next_retry_at TIMESTAMP,
    last_error TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS ix_outbox_events_status_next_retry
    ON outbox_events (status, next_retry_at);
