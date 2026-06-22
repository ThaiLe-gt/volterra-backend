# Roadmap

## Phase 1: Scaffold

Status: done.

Scope:

- Maven monorepo.
- Java 25 and Spring Boot 4.
- Shared libraries.
- Service modules.
- Route stubs for current `.NET` function coverage.
- Local infrastructure compose.
- Event primitives and local recording publisher.

## Phase 2: Core Persistence

Scope:

- PostgreSQL/Flyway persistence for `dt-auth-svc`.
- PostgreSQL/Flyway persistence for `dt-operation-svc`.
- Tenant/site/device/data-point CRUD.
- RBAC and data-permission model.
- Site IoT config versioning.

## Phase 3: Legacy Adapter

Scope:

- Typed HTTP adapters in `dt-legacy-dotnet-svc`.
- `.NET` response-envelope mapping.
- PLC command delegation.
- OCPP command delegation.
- Compatibility route validation against current frontend.

## Phase 4: Telemetry Foundation

Scope:

- ClickHouse schema and write adapters.
- Telemetry raw and normalized writes.
- Latest-value Redis cache.
- History and aggregate queries.
- Realtime endpoint.

## Phase 5: Command Platform

Scope:

- Command lifecycle state machine.
- Idempotency keys.
- Approval policies.
- Permission checks.
- Execution delegation.
- Audit events in ClickHouse.

## Phase 6: Energy Read Models

Scope:

- EV charge point read models.
- Connector status.
- Charger tags and transactions.
- BESS status and schedule views.
- Energy dashboard projections.

## Phase 7: Event Bus Productionization

Scope:

- Kafka/Redpanda producer adapter.
- Transactional outbox worker.
- Retry and DLQ.
- Idempotent consumer tracking.
- ClickHouse event history.
- Event replay procedure.

## Phase 8: Files And Alarms

Scope:

- File metadata persistence.
- Local and MinIO/S3 storage providers.
- Upload validation.
- Alarm rules.
- Alarm event lifecycle.
- Alarm notifications.

## Phase 9: Edge/Site Agent

Scope:

- Site agent receives config from `dt-operation-svc`.
- Polls PLC locally.
- Publishes telemetry to `dt-telemetry-svc`.
- Executes approved commands.
- Supports offline buffering.

## Phase 10: Data Center Extension

Scope:

- Add data center asset templates.
- UPS/PDU/chiller/rack/room equipment types.
- Data center telemetry profiles.
- Capacity and thermal dashboard read models.
- Reuse the same tenant/site/asset/device/data-point foundation.
