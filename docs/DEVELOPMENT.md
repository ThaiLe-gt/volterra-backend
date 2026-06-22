# Development Guide

## Build

```powershell
cd D:\WORKS\PERSONAL\VIN\Voltera\New-Volterra\backend
.\mvnw.cmd clean package
```

Build one module and its dependencies:

```powershell
.\mvnw.cmd -pl services/dt-operation-svc -am package
```

## Local Infrastructure

```powershell
docker compose -f docker-compose.local.yml up -d
```

Infrastructure ports:

| Component | Port |
| --- | --- |
| PostgreSQL | `5434` |
| ClickHouse HTTP | `8123` |
| ClickHouse native | `9000` |
| Redis | `6380` |
| Redpanda Kafka API | `9092` |
| Redpanda admin | `9644` |
| MinIO API | `9010` |
| MinIO Console | `9011` |

## Run A Service

```powershell
.\mvnw.cmd -pl services/dt-operation-svc spring-boot:run
```

Service ports:

| Service | Port |
| --- | --- |
| `dt-gateway-svc` | `8080` |
| `dt-auth-svc` | `8081` |
| `dt-operation-svc` | `8082` |
| `dt-telemetry-svc` | `8083` |
| `dt-energy-svc` | `8084` |
| `dt-command-svc` | `8085` |
| `dt-alarm-svc` | `8086` |
| `dt-file-svc` | `8087` |
| `dt-legacy-dotnet-svc` | `8090` |

## Add A New Use Case

1. Put business rules in the bounded context `domain` package.
2. Add an application use case in `application`.
3. Define ports for external dependencies.
4. Add REST DTOs and controller code in `adapter/in/web`.
5. Add persistence, ClickHouse, Redis, legacy, or messaging adapters under `adapter/out/*`.
6. Publish domain/integration events through `EventPublisher` where another service needs to react.
7. Add database migration files under `src/main/resources/db/migration`.

## Test Plan

Future test coverage:

- Architecture tests: domain has no Spring/JPA/Redis/ClickHouse/HTTP dependency.
- Auth tests: token issue, refresh, revoke, invalid token, expired token, role permission, data permission.
- Operation tests: tenant/site CRUD, device registration, data point registration, duplicate code, invalid Modbus/OCPP mapping, config versioning.
- Telemetry tests: ClickHouse insert, latest query, history query, aggregate query, stale quality event, tenant/site scope filter.
- Command tests: idempotency duplicate, approval required, unauthorized control, timeout, retry, `.NET` failure mapping.
- Energy/OCPP tests: charger list, tag list, active transaction, connector state, OCPP command delegation, config update delegation.
- File tests: extension whitelist/blacklist, size limit, local storage, MinIO storage, presign, replace, delete.
- Gateway compatibility tests: current Vue routes still resolve during migration.

## Assumptions

- Use `dt-*` naming.
- No `*-client` / `*-app` module split.
- Each `dt-*-svc` is one deployable Spring Boot service.
- Java 25 is the runtime target.
- Spring Boot 4.0.x is the framework baseline.
- Existing `.NET web-energy/api` remains online during v1.
- ClickHouse replaces TimescaleDB for new telemetry storage, but existing TimescaleDB can remain until migration is complete.
- Data center support will reuse the same `asset/device/datapoint` model after EV charging and BESS are stable.
