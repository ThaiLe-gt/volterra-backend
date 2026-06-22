# Legacy .NET Migration

## Current Role Of `.NET`

The existing backend at `../../web-energy/api` remains the v1 execution runtime for:

- PLC Modbus polling.
- PLC Modbus control writes.
- OCPP WebSocket protocol handling.
- Existing route compatibility while frontend migration is in progress.

Java owns the new platform layer and gradually absorbs API ownership.

## Function Coverage

| Current `.NET` area | New Java owner |
| --- | --- |
| Auth login/refresh/logout | `dt-auth-svc` |
| Account/profile management | `dt-auth-svc` |
| Role/permission model | `dt-auth-svc`, `dt-permission-lib` |
| Province/district/ward | `dt-operation-svc` |
| Station CRUD | `dt-operation-svc` as Site |
| Device CRUD | `dt-operation-svc` |
| Signal CRUD | `dt-operation-svc` as DataPoint |
| Control signals | `dt-operation-svc` config + `dt-command-svc` execution |
| PLC polling | v1 `.NET`; future edge/site agent |
| PLC control | `dt-command-svc` lifecycle, v1 `.NET` execution |
| PLC signal logs | `dt-command-svc` + ClickHouse event tables |
| Latest telemetry | `dt-telemetry-svc` |
| Historical telemetry | `dt-telemetry-svc` |
| Timescale sampling views | ClickHouse aggregate tables/projections |
| SignalR realtime | `dt-telemetry-svc` realtime endpoint |
| Charger points/tags/transactions | `dt-energy-svc` |
| Charger connectors | `dt-energy-svc` |
| OCPP WebSocket | v1 `.NET`; future Java OCPP runtime if needed |
| OCPP central-system commands | `dt-command-svc` + `dt-legacy-dotnet-svc` |
| Charger logs/control logs | ClickHouse event tables |
| BESS schedule job | `dt-command-svc` |
| Auto-off setpoint job | `dt-command-svc` |
| File upload/download/list/delete | `dt-file-svc` |
| Local/S3/MinIO storage | `dt-file-svc`, `dt-file-lib` |
| LogBull/observability | `dt-observability-lib` |
| Swagger/OpenAPI | service-level Spring/OpenAPI later |
| Debug/weather sample APIs | not carried forward as production APIs |

## API Migration Strategy

Preferred new APIs use `/api/v1/*`.

Temporary compatibility routes:

```text
/auth/*                 -> dt-auth-svc
/manage/account/*       -> dt-auth-svc
/profile/*              -> dt-auth-svc
/station/*              -> dt-operation-svc or dt-legacy-dotnet-svc
/signal/latest          -> dt-telemetry-svc or dt-legacy-dotnet-svc
/signal/list-*          -> dt-telemetry-svc or dt-legacy-dotnet-svc
/signal/control-*       -> dt-command-svc
/ocpp/*                 -> dt-command-svc -> dt-legacy-dotnet-svc
/manage/charge/*        -> dt-energy-svc or dt-legacy-dotnet-svc
/common/files/*         -> dt-file-svc
/manage/files/*         -> dt-file-svc
/realtime-signal        -> dt-telemetry-svc realtime endpoint
```

Do not carry forward as production APIs:

- `WeatherForecastController`
- `PlcTestController`
- unauthenticated cache clearing

## OCPP Coverage

Current charge-point initiated handlers in `.NET`:

- `Authorize`
- `BootNotification`
- `Heartbeat`
- `MeterValues`
- `StartTransaction`
- `StopTransaction`
- `StatusNotification`
- `DataTransfer`
- `DiagnosticsStatusNotification`
- `FirmwareStatusNotification`

Current central-system commands in `.NET`:

- `RemoteStartTransaction`
- `RemoteStopTransaction`
- `UnlockConnector`
- `ReserveNow`
- `CancelReservation`
- `GetCompositeSchedule`
- `GetConfiguration`
- `ChangeConfiguration`
- `SetChargingProfile`
- `ClearChargingProfile`
- `GetDiagnostics`
- `Reset`
- `ClearCache`
- `ChangeAvailability`
- `TriggerMessage`
- `DataTransfer`
- `GetLocalListVersion`
- `SendLocalList`
- `UpdateFirmware`

Migration:

- v1 keeps OCPP WebSocket and protocol handling in `.NET`.
- Java exposes command/read APIs and delegates execution to `.NET`.
- Store OCPP command events and message summaries in ClickHouse.
- Add native Java OCPP runtime later only if charger traffic scale justifies it.

## PLC And Edge Coverage

Current `.NET` behavior:

- `PlcPollingService`: reads active stations every 15 seconds.
- `ModbusConnectionManager`: maintains Modbus TCP connection per station.
- `ObserveSignal`: reads Modbus registers and writes `signal_readable`.
- `PlcControlService`: periodic control loop.
- `SignalControlService`: writes Modbus control registers.
- `PlcLoggerService`: stores PLC signal logs.

Migration:

- v1 keeps PLC polling/control in `.NET`.
- Java owns site/device/data-point configuration and command lifecycle.
- `dt-command-svc` delegates PLC writes to `.NET`.
- `dt-telemetry-svc` initially ingests from `.NET` or proxies reads through `dt-legacy-dotnet-svc`.
- Future edge/site agent receives config from `dt-operation-svc`, polls PLC locally, publishes telemetry to `dt-telemetry-svc`, and executes approved commands.
