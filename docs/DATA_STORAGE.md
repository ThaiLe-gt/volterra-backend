# Data Storage

## PostgreSQL

PostgreSQL stores transactional platform state.

Planned tables:

```text
auth_accounts
auth_roles
auth_permissions
auth_role_permissions
auth_account_roles
auth_data_permissions

tenants
sites
locations
assets
devices
data_points
device_templates
data_point_templates
protocol_mappings
site_iot_config_versions
commissioning_records

commands
command_approvals
command_audit_logs
outbox_events

alarm_rules
alarm_event_state

file_assets
```

## ClickHouse

ClickHouse replaces TimescaleDB for new telemetry, aggregate, audit, and event-history storage.

Planned tables:

```text
telemetry_raw_events
telemetry_points
telemetry_latest_projection
telemetry_aggregate_1m
telemetry_aggregate_5m
telemetry_aggregate_10m
telemetry_aggregate_15m
telemetry_aggregate_20m
telemetry_aggregate_30m
telemetry_aggregate_1h
telemetry_aggregate_1d
telemetry_aggregate_1w
telemetry_aggregate_1mo
command_events
alarm_events
ocpp_message_events
plc_signal_events
charger_meter_events
```

Telemetry row shape:

```text
tenant_id
site_id
asset_id
device_id
data_point_id
data_point_code
source_protocol
event_time
ingest_time
value_float
value_string
value_bool
unit
quality
raw_payload_json
```

## Redis

Redis stores short-lived state:

```text
latest telemetry cache
idempotency keys
token/session cache
short-lived command status
realtime fanout state
```

## Object Storage

S3-compatible storage stores binary assets:

```text
uploaded files
diagnostics
reports
site IoT config packages
```

Local development uses MinIO.

## Data Ownership

| Data | Owner |
| --- | --- |
| Accounts, roles, permissions | `dt-auth-svc` |
| Tenants, sites, locations, assets, devices, data points | `dt-operation-svc` |
| Telemetry and aggregates | `dt-telemetry-svc` |
| EV/BESS read models | `dt-energy-svc` |
| Command lifecycle | `dt-command-svc` |
| Alarm rules and alarm state | `dt-alarm-svc` |
| File metadata | `dt-file-svc` |
| File binary content | Object storage through `dt-file-svc` |

## Migration Notes

- Existing TimescaleDB data can remain available until Java read models are complete.
- New high-volume telemetry should land in ClickHouse.
- Historical migration should be done with batch jobs that preserve site/device/data-point identifiers.
- Dashboards should read from Java ClickHouse projections once parity is proven.
