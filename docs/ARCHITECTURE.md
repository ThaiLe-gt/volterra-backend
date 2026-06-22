# Architecture

## Goal

Build a general digital twin backend for EV charging, BESS, and later data center energy operations. The platform must support configurable sites, assets, IoT devices, telemetry, commands, alarms, files, authorization, and integration with the existing `.NET` backend during migration.

## Runtime Baseline

- Java 25.
- Spring Boot 4.0.x.
- Maven monorepo.
- One deployable Spring Boot module per service.
- PostgreSQL for transactional state.
- ClickHouse for telemetry, audit, event history, and high-volume time-series reads.
- Redis for latest values, idempotency, session/token cache, and realtime fanout state.
- Redpanda/Kafka-compatible broker for service events.
- MinIO/S3-compatible object storage for uploaded files, diagnostics, reports, and config packages.

## Architecture Style

The service implementation follows DDD and hexagonal architecture:

- `domain`: entities, value objects, domain services, domain events, repository ports.
- `application`: use cases, command/query handlers, input/output ports.
- `adapter/in/web`: REST controllers and HTTP DTOs.
- `adapter/out/persistence`: JPA entities, Spring Data repositories, repository adapters.
- `adapter/out/clickhouse`: ClickHouse query and write adapters.
- `adapter/out/redis`: cache/session/latest-value adapters.
- `adapter/out/legacy`: calls to `.NET web-energy/api`.

Domain code must not depend on Spring, JPA, HTTP clients, Redis, ClickHouse, or framework annotations.

## Service Boundaries

| Service | Boundary |
| --- | --- |
| `dt-gateway-svc` | Public API gateway and future compatibility routing. |
| `dt-auth-svc` | Authentication, accounts, roles, RBAC, data permissions. |
| `dt-operation-svc` | Tenant/site/asset/device/data-point setup and commissioning. |
| `dt-telemetry-svc` | Telemetry ingest, latest values, history, aggregates, realtime feed. |
| `dt-energy-svc` | EV charging, BESS, and energy dashboard read models. |
| `dt-command-svc` | Command lifecycle, approval, idempotency, dispatch, audit. |
| `dt-alarm-svc` | Alarm rules, alarm events, acknowledgement, close workflow. |
| `dt-file-svc` | File upload, storage, metadata, download, presign. |
| `dt-legacy-dotnet-svc` | Anti-corruption layer around current `.NET` APIs. |

## Core Digital Twin Model

The platform uses a general model instead of EV-only or BESS-only tables:

```text
Tenant
  -> Site
    -> Location / Zone / Building
      -> System
        -> Asset / Equipment
          -> Device
            -> DataPoint
```

This supports:

- EV charging sites.
- BESS and power conversion equipment.
- Solar, grid, wind, weather, and meters.
- Future data center rooms, racks, UPS, PDUs, chillers, CRAC units, and sensors.

## Auth And Authorization

`dt-auth-svc` owns identity and authorization:

- Login, refresh, revoke/logout.
- Account CRUD.
- Profile read/update/password change.
- Role CRUD.
- Permission CRUD.
- RBAC feature permissions.
- Data permissions scoped by tenant, site, location, asset, and device.

`dt-security-lib` contains shared current-user and tenant context primitives. `dt-permission-lib` contains permission operation definitions and will later host reusable authorization helpers.

## Legacy Integration

The current `.NET` backend remains online during v1:

- PLC polling/control stays in `.NET`.
- OCPP WebSocket handling stays in `.NET`.
- Java owns site/device/data-point configuration, telemetry storage, command lifecycle, API compatibility, and new platform features.
- `dt-legacy-dotnet-svc` isolates Java services from old response envelopes and endpoint shapes.

## Deployment Direction

The current scaffold is a monorepo for development speed. Each service remains separately deployable and can later move to independent pipelines if needed.
