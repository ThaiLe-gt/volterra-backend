# Backend Documentation

This folder contains the detailed backend plan for the Java 25 and Spring Boot 4 digital twin platform.

## Reading Order

1. [Architecture](ARCHITECTURE.md)
2. [Repository Structure](REPOSITORY_STRUCTURE.md)
3. [Service Catalog](SERVICE_CATALOG.md)
4. [Event-Driven Design](EVENT_DRIVEN.md)
5. [Data Storage](DATA_STORAGE.md)
6. [Platform Workflows](WORKFLOWS.md)
7. [Legacy .NET Migration](MIGRATION_FROM_DOTNET.md)
8. [Development Guide](DEVELOPMENT.md)
9. [Roadmap](ROADMAP.md)

## What Each Document Covers

| Document | Purpose |
| --- | --- |
| [Architecture](ARCHITECTURE.md) | Platform goals, runtime baseline, DDD/hexagonal rules, service boundaries, auth, and legacy integration. |
| [Repository Structure](REPOSITORY_STRUCTURE.md) | Maven monorepo layout, naming convention, standard service package structure, and dependency direction. |
| [Service Catalog](SERVICE_CATALOG.md) | Responsibilities, current `.NET` coverage, initial endpoints, and compatibility endpoints for every service. |
| [Event-Driven Design](EVENT_DRIVEN.md) | Event primitives, topics, publication points, transactional outbox plan, consumer rules, and event history. |
| [Data Storage](DATA_STORAGE.md) | PostgreSQL, ClickHouse, Redis, object storage, data ownership, and migration notes. |
| [Platform Workflows](WORKFLOWS.md) | Site commissioning, IoT setup, telemetry ingestion/query, command approval, OCPP delegation, alarms, files, and auth flows. |
| [Legacy .NET Migration](MIGRATION_FROM_DOTNET.md) | Mapping from current `.NET web-energy/api` functions to the new Java services. |
| [Development Guide](DEVELOPMENT.md) | Build commands, local infrastructure, service ports, use-case implementation pattern, test plan, and assumptions. |
| [Roadmap](ROADMAP.md) | Phased delivery plan from scaffold to edge/site agent and data center extension. |
