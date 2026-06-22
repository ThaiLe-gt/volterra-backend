CREATE TABLE IF NOT EXISTS telemetry_raw_events (
    tenant_id String,
    site_id String,
    asset_id String,
    device_id String,
    data_point_id String,
    data_point_code String,
    source_protocol String,
    event_time DateTime64(3),
    ingest_time DateTime64(3),
    value_float Nullable(Float64),
    value_string Nullable(String),
    value_bool Nullable(Bool),
    unit Nullable(String),
    quality String,
    raw_payload_json String
) ENGINE = MergeTree
PARTITION BY toYYYYMM(event_time)
ORDER BY (tenant_id, site_id, data_point_id, event_time);

CREATE TABLE IF NOT EXISTS command_events (
    tenant_id String,
    site_id String,
    command_id String,
    event_type String,
    event_time DateTime64(3),
    payload_json String
) ENGINE = MergeTree
PARTITION BY toYYYYMM(event_time)
ORDER BY (tenant_id, site_id, command_id, event_time);

CREATE TABLE IF NOT EXISTS alarm_events (
    tenant_id String,
    site_id String,
    alarm_id String,
    event_type String,
    event_time DateTime64(3),
    payload_json String
) ENGINE = MergeTree
PARTITION BY toYYYYMM(event_time)
ORDER BY (tenant_id, site_id, alarm_id, event_time);

CREATE TABLE IF NOT EXISTS ocpp_message_events (
    tenant_id String,
    site_id String,
    charge_point_id String,
    event_type String,
    event_time DateTime64(3),
    payload_json String
) ENGINE = MergeTree
PARTITION BY toYYYYMM(event_time)
ORDER BY (tenant_id, site_id, charge_point_id, event_time);
