# Repository Structure

## Root

```text
New-Volterra/
  backend/
    pom.xml
    README.md
    docker-compose.local.yml
    mvnw
    mvnw.cmd
    docs/

    shared/
      dt-common-lib/
      dt-common-web-lib/
      dt-security-lib/
      dt-observability-lib/
      dt-event-lib/
      dt-permission-lib/
      dt-clickhouse-lib/
      dt-file-lib/

    services/
      dt-gateway-svc/
      dt-auth-svc/
      dt-operation-svc/
      dt-telemetry-svc/
      dt-energy-svc/
      dt-command-svc/
      dt-alarm-svc/
      dt-file-svc/
      dt-legacy-dotnet-svc/
```

## Naming Convention

Services use the `-svc` suffix:

- `dt-auth-svc`
- `dt-operation-svc`
- `dt-telemetry-svc`
- `dt-energy-svc`
- `dt-command-svc`
- `dt-alarm-svc`
- `dt-file-svc`
- `dt-legacy-dotnet-svc`

Shared libraries use the `-lib` suffix:

- `dt-common-lib`
- `dt-security-lib`
- `dt-event-lib`
- `dt-clickhouse-lib`

Do not split each service into `app` and `client` submodules. Each `dt-*-svc` is one deployable Spring Boot application.

## Standard Service Layout

Example:

```text
dt-operation-svc/
  pom.xml
  src/main/java/com/dt/operation/
    OperationServiceApplication.java

    site/
      domain/
        Site.java
        SiteId.java
        SiteRepository.java
      application/
        CreateSiteUseCase.java
        UpdateSiteUseCase.java
        GetSiteQuery.java
      adapter/
        in/web/
          SiteController.java
          CreateSiteRequest.java
          SiteResponse.java
        out/persistence/
          SiteJpaEntity.java
          SpringDataSiteRepository.java
          JpaSiteRepositoryAdapter.java
        out/messaging/
          SiteEventPublisher.java

    asset/
      domain/
      application/
      adapter/

    device/
      domain/
      application/
      adapter/

    datapoint/
      domain/
      application/
      adapter/

    commissioning/
      domain/
      application/
      adapter/

    shared/
      domain/
      application/

    config/

  src/main/resources/
    application.yml
    application-local.yml
    db/migration/
```

## Shared Libraries

| Module | Responsibility |
| --- | --- |
| `dt-common-lib` | API envelope, pagination, IDs, time ranges, domain exceptions. |
| `dt-common-web-lib` | Global exception handling, validation error format, web conventions. |
| `dt-security-lib` | Current user, tenant context, future JWT helpers. |
| `dt-observability-lib` | Correlation IDs, tracing/logging constants, future metrics helpers. |
| `dt-event-lib` | Event envelope, topic names, outbox primitives, local recording publisher. |
| `dt-permission-lib` | Permission operation enum and future RBAC/data-permission checks. |
| `dt-clickhouse-lib` | Shared ClickHouse table names and future client/query helpers. |
| `dt-file-lib` | File storage provider enum and future storage abstraction. |

## Dependency Direction

Allowed direction:

```text
adapter -> application -> domain
```

Shared libraries may be used by services, but shared libraries must stay generic and must not depend on service modules.
