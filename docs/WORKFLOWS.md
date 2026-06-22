# Platform Workflows

## Site Setup And Commissioning

```text
Admin creates tenant/site
  -> configure address/location hierarchy
  -> register assets/equipment
  -> register devices
  -> map data points to device protocols
  -> validate Modbus/OCPP mappings
  -> publish site configuration version
  -> commission site
  -> edge/.NET runtime receives or reads configuration
```

Primary owner: `dt-operation-svc`.

Important checks:

- Site code must be unique inside tenant scope.
- Device code must be unique inside site scope.
- Data point code must be unique inside device or asset scope.
- Protocol mapping must be valid before commissioning.
- Commissioned config should be versioned and auditable.

## IoT Device Setup

```text
Create device
  -> choose protocol type
  -> attach to asset/equipment
  -> define data points
  -> define polling/write settings
  -> test connectivity or delegate test to .NET/edge runtime
  -> activate device
```

Protocol examples:

- Modbus TCP for PLC and meters.
- OCPP for EV chargers.
- Future MQTT/HTTP adapters for edge devices.

## Telemetry Ingestion

```text
.NET PLC/OCPP runtime or future edge agent
  -> sends telemetry to dt-telemetry-svc
  -> validate tenant/site/device/data-point mapping
  -> write raw payload to ClickHouse
  -> normalize data point values
  -> update latest value cache
  -> publish telemetry event
  -> update aggregates/projections
  -> notify realtime subscribers
```

Primary owner: `dt-telemetry-svc`.

Quality values should distinguish:

- good
- stale
- invalid
- missing
- estimated
- communication_failed

## Telemetry Query

```text
Frontend dashboard
  -> request latest/history/aggregate data
  -> dt-telemetry-svc enforces tenant/site scope
  -> latest data comes from Redis or ClickHouse projection
  -> history and aggregate data comes from ClickHouse
```

## Command Lifecycle

```text
User requests command
  -> dt-command-svc validates idempotency key
  -> checks RBAC and data permission
  -> evaluates approval policy
  -> records command
  -> publishes CommandRequested
  -> command waits for approval or dispatches directly
  -> execution delegates to .NET through dt-legacy-dotnet-svc
  -> result is recorded
  -> command event is written to ClickHouse
```

Sensitive command examples:

- Grid breaker control.
- BESS charge/discharge schedule.
- Solar control.
- EV remote start/stop.
- OCPP reset/change configuration.

## Command Approval

```text
Approver opens pending command
  -> dt-command-svc checks approve permission
  -> approval is recorded
  -> CommandApproved event is published
  -> command dispatcher sends execution request
```

Approval policy should be configurable by tenant/site, command type, asset type, and risk level.

## OCPP Command Delegation

```text
Frontend/API client
  -> dt-command-svc creates OCPP command
  -> dt-command-svc delegates execution
  -> dt-legacy-dotnet-svc maps request to .NET OCPP route
  -> .NET sends command through current OCPP WebSocket runtime
  -> Java records command and OCPP events
```

OCPP WebSocket runtime stays in `.NET` for v1.

## Alarm Workflow

```text
Telemetry/command/event input
  -> dt-alarm-svc evaluates rules
  -> creates alarm event
  -> publishes AlarmRaised
  -> operator acknowledges alarm
  -> operator closes alarm after condition clears
```

Alarm examples:

- Stale signal.
- Device offline.
- Charger fault.
- BESS SOC below minimum.
- Command failed or timed out.

## File Workflow

```text
User uploads file
  -> dt-file-svc validates extension and size
  -> stores file in local storage or MinIO/S3
  -> records file metadata
  -> returns file id and download/presign details
```

File categories:

- Site configuration package.
- Charger diagnostics.
- Reports.
- User-uploaded documents.

## Auth Workflow

```text
User logs in
  -> dt-auth-svc validates credentials
  -> token is issued
  -> gateway/services read current user context
  -> service checks feature permission
  -> service checks data permission by tenant/site/asset/device
```

RBAC decides what action a user may perform. Data permission decides where the user may perform it.
