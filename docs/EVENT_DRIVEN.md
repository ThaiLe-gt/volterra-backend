# Event-Driven Design

## Current Implementation

The scaffold includes the first event-driven layer in `shared/dt-event-lib`.

Implemented:

- `EventEnvelope<T>`: standard event wrapper.
- `EventMetadata`: event id, type, aggregate, tenant/site, correlation/causation, timestamp, schema version.
- `DomainEvent` and `IntegrationEvent` marker interfaces.
- `EventPublisher` port.
- `RecordingEventPublisher`: local in-memory publisher for scaffolding and tests.
- `EventTopicNames`: canonical topic names.
- `OutboxEvent` and `OutboxStatus`: outbox primitives for PostgreSQL-backed services.
- `GET /api/v1/events/published`: local debug endpoint available in services that include `dt-event-lib`.
- Redpanda in `docker-compose.local.yml`.

Current publisher behavior:

- Events are recorded in memory.
- Events are not yet sent to Redpanda.
- This keeps service modules compile-safe while the transactional outbox and Kafka adapter are implemented.

## Topics

```text
dt.operation.site-events
dt.operation.device-events
dt.telemetry.raw-events
dt.telemetry.normalized-events
dt.command.events
dt.alarm.events
dt.energy.ev-events
dt.energy.bess-events
dt.ocpp.events
dt.audit.events
```

## Initial Event Publication Points

- `dt-operation-svc`: publishes `SiteCreated` on site creation.
- `dt-telemetry-svc`: publishes `TelemetryRawReceived` on telemetry ingest.
- `dt-command-svc`: publishes `CommandRequested`, `CommandApproved`, `CommandCancelled`.
- `dt-alarm-svc`: publishes `AlarmAcknowledged`.
- `dt-legacy-dotnet-svc`: publishes `OcppCommandDelegated`.

## Transactional Outbox Plan

PostgreSQL-backed services should write domain state and outbox rows in the same transaction.

Flow:

```text
HTTP request
  -> application use case
  -> domain changes
  -> repository save
  -> outbox row insert
  -> transaction commit
  -> outbox publisher worker reads pending rows
  -> publish to Redpanda topic
  -> mark outbox row published
```

Outbox row fields:

- `event_id`
- `event_type`
- `aggregate_type`
- `aggregate_id`
- `tenant_id`
- `site_id`
- `correlation_id`
- `causation_id`
- `topic`
- `payload_json`
- `status`
- `retry_count`
- `next_retry_at`
- `created_at`
- `published_at`

## Consumer Rules

Consumers must be idempotent:

- Track processed `eventId`.
- Treat duplicate messages as success.
- Store consumer offsets through the broker.
- Persist important read-model changes transactionally where possible.

For cross-store writes, prefer:

- idempotent ClickHouse inserts with stable event ids.
- upsert latest projections in Redis/PostgreSQL.
- replayable consumers for derived read models.

## Event History

Immutable event history should be stored in ClickHouse for audit and high-volume query:

- `command_events`
- `alarm_events`
- `ocpp_message_events`
- `plc_signal_events`
- `charger_meter_events`
- `telemetry_raw_events`
- `telemetry_points`

## Next Implementation Steps

1. Add Kafka/Redpanda producer adapter in `dt-event-lib` or a dedicated `dt-event-kafka-lib`.
2. Add transactional outbox persistence for PostgreSQL-backed services.
3. Add outbox publisher worker with retry and DLQ.
4. Add consumer adapters for telemetry, alarms, energy read models, and command execution results.
5. Add idempotent consumer tracking by `eventId`.
6. Persist immutable event history into ClickHouse event tables.
7. Add tracing context propagation through event metadata.
