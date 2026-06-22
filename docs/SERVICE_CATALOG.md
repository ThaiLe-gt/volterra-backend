# Service Catalog

## `dt-gateway-svc`

Public API gateway and future BFF.

Responsibilities:

- Public route discovery.
- Future JWT validation.
- Future rate limits for control commands.
- Future compatibility routing from old frontend paths to new `/api/v1/*` services.

Initial endpoint:

- `GET /api/v1/gateway/routes`

## `dt-auth-svc`

Authentication, account, profile, role, permission, and data-permission service.

Replaces current `.NET` areas:

- `AuthController`
- `AccountController`
- `ProfileController`
- `AccountService`
- `AuthSupport`
- `Account`, `Role`, `Permission`, `AccountRole`, `RolePermission`

Target responsibilities:

- OAuth2/OIDC with Spring Authorization Server.
- Login, refresh, revoke/logout.
- Account CRUD.
- Profile read/update/password change.
- Role CRUD.
- Permission CRUD.
- RBAC feature permissions.
- Data permissions by tenant/site/location/asset/device.

Initial endpoints:

- `POST /api/v1/auth/login`
- `POST /api/v1/auth/refresh`
- `GET /api/v1/auth/logout`
- `GET /api/v1/accounts`
- `GET /api/v1/profile`
- `PUT /api/v1/profile`
- `PUT /api/v1/profile/password`
- `GET /api/v1/permissions/operations`
- `GET /api/v1/data-permissions`

Compatibility endpoints:

- `POST /auth/login`
- `POST /auth/refresh`
- `GET /auth/logout`
- `GET /manage/account`
- `GET /profile`
- `PUT /profile/update`
- `PUT /profile/password`

## `dt-operation-svc`

Main configuration and control-plane service.

Replaces current `.NET` operation/configuration logic:

- `StationController`
- `AddressController`
- Station/device/signal parts of `StationService`
- `Station`, `Device`, `Signal`, `ControlSignal`
- `Province`, `District`, `Ward`

Target responsibilities:

- Tenant/client setup.
- Site CRUD and commissioning status.
- Address/geography APIs.
- Location/zone/building management.
- Asset/equipment registry.
- Device registry.
- Data point registry.
- Device templates and data point templates.
- Modbus mapping: host, port, slave id, register address, register count, data type, poll rate.
- OCPP mapping: charge point id, connector count, auth type, protocol.
- Import/export site IoT configuration.
- Cached site/detail replacement for the current station-detail cache.

Initial endpoints:

- `GET /api/v1/sites`
- `POST /api/v1/sites`
- `GET /api/v1/assets`
- `POST /api/v1/assets`
- `GET /api/v1/devices`
- `POST /api/v1/devices`
- `GET /api/v1/data-points`
- `POST /api/v1/data-points`
- `GET /api/v1/sites/{siteId}/iot-configs`
- `POST /api/v1/sites/{siteId}/commissioning`

Compatibility endpoints:

- `GET /station/list`
- `POST /station`
- `GET /station/{siteId}/devices`
- `POST /station/{siteId}/device`
- `GET /station/{siteId}/device-{deviceId}/signals`
- `POST /station/{siteId}/device-{deviceId}/signal`

## `dt-telemetry-svc`

Telemetry ingestion, latest values, history, aggregates, and realtime feed.

Replaces current `.NET` telemetry read/storage behavior:

- Read side of `SignalController`
- `SignalReaderService`
- `SignalSamplingJob`
- SignalR latest-data behavior
- `SignalReadable`
- Timescale materialized views, replaced by ClickHouse tables/projections

Target responsibilities:

- Store normalized data point telemetry in ClickHouse.
- Store raw payloads for audit/debug.
- Query latest telemetry by site/device/data point.
- Query history by system/grid/wind/solar/BESS/charger/weather.
- Query aggregates by 1m, 5m, 10m, 15m, 20m, 30m, 1h, day, week, month.
- Replace `SaveWhenFail` with telemetry quality/staleness events.
- Publish realtime latest-value events through Redis/WebSocket/SSE.

Initial endpoints:

- `GET /api/v1/telemetry/latest`
- `GET /api/v1/telemetry/latest-raw`
- `GET /api/v1/telemetry/history`
- `GET /api/v1/telemetry/history/system`
- `GET /api/v1/telemetry/history/grid`
- `GET /api/v1/telemetry/history/wind`
- `GET /api/v1/telemetry/history/solar`
- `GET /api/v1/telemetry/history/bess`
- `GET /api/v1/telemetry/history/charger`
- `GET /api/v1/telemetry/history/weather`
- `POST /api/v1/telemetry/ingest`

Compatibility endpoints:

- `GET /signal/latest`
- `GET /signal/latest-raw`
- `GET /signal/historical-list`
- `GET /signal/list-system-energy`
- `GET /signal/list-grid`
- `GET /signal/list-wind`
- `GET /signal/list-solar`
- `GET /signal/list-bess`
- `GET /signal/list-charger`
- `GET /signal/list-weather`

## `dt-energy-svc`

EV charging, BESS, and energy dashboard service.

Replaces current energy/charger domain read models:

- `ChargeController`
- Charger read-side of `StationController`
- EV/BESS dashboard portions of `SignalController`
- Parts of `ChargePointService`
- `ChargerPoint`, `ChargerConnector`, `ChargerTag`, `ChargerTransaction`

Target responsibilities:

- EV charge point management views.
- Connector status.
- Charger tag list.
- Active and historical charger transactions.
- Charge point configuration view.
- BESS status and schedule view.
- Solar/grid/BESS/charger response models.
- Public/private charging policy fields such as `allow_public` and `limit_hours`.

Initial endpoints:

- `GET /api/v1/ev/charge-points`
- `GET /api/v1/ev/charge-tags`
- `GET /api/v1/ev/transactions`
- `GET /api/v1/ev/active-transactions`
- `GET /api/v1/ev/charge-points/{chargePointId}/connectors`
- `GET /api/v1/bess/status`
- `GET /api/v1/bess/schedules`
- `GET /api/v1/energy/dashboard/latest`

Compatibility endpoints:

- `GET /manage/charge/list-charger-point`
- `GET /manage/charge/list-charger-tag`
- `GET /manage/charge/list-charger-transaction`
- `GET /manage/charge/list-active-transaction`
- `GET /station/{siteId}/charger-connectors`

## `dt-command-svc`

Command lifecycle, control, approval, and audit.

Replaces current `.NET` control behavior:

- Write/control side of `SignalController`
- `SignalControlService`
- `PlcControlService`
- `BessScheduleJob`
- `ControlSignalAutoOffSetPoint`
- `PlcLoggerService`
- `ChargerControlLog`

Target responsibilities:

- Generic command lifecycle: requested, pending approval, approved, dispatched, succeeded, failed, timed out, cancelled.
- Idempotency key for every command.
- Approval policy for sensitive commands.
- Permission checks for grid, solar, BESS, EV charger, and approvals.
- Grid MCCB control.
- Solar control.
- BESS control.
- BESS charge/discharge schedule commands.
- Auto-off set point equivalent.
- PLC command audit.
- OCPP command audit.
- v1 execution delegation to `.NET` through `dt-legacy-dotnet-svc`.

Initial endpoints:

- `POST /api/v1/commands`
- `GET /api/v1/commands/{commandId}`
- `POST /api/v1/commands/{commandId}/approve`
- `POST /api/v1/commands/{commandId}/cancel`

Compatibility endpoints:

- `GET /signal/control-grid`
- `POST /signal/control-grid`
- `GET /signal/control-solar`
- `POST /signal/control-solar`
- `GET /signal/control-bess`
- `POST /signal/control-bess`
- `GET /signal/control-charge-discharge`
- `POST /signal/control-charge-discharge`

## `dt-alarm-svc`

Alarm rules and alarm-event workflow.

Target responsibilities:

- Stale signal detection.
- Site/device offline alarms.
- Threshold alarms.
- Charger fault alarms.
- BESS SOC min/max alarms.
- Command failure alarms.
- Alarm acknowledge/close workflow.

Initial endpoints:

- `GET /api/v1/alarm-rules`
- `POST /api/v1/alarm-rules`
- `GET /api/v1/alarm-events`
- `POST /api/v1/alarm-events/{eventId}/acknowledge`

## `dt-file-svc`

File storage and file asset service.

Replaces current `.NET` file behavior:

- `CommonFileController`
- `FileManagerController`
- `FileAssetService`
- `LocalFileStorageService`
- `MinioFileStorageService`
- `FileUploadValidator`
- `FileAsset`

Target responsibilities:

- Upload/list/download/delete/presign.
- Upload and record file asset metadata.
- Replace file.
- Diagnostic upload.
- Whitelist/blacklist extension validation.
- Max file size validation.
- Local and S3/MinIO providers.

Initial endpoints:

- `POST /api/v1/files/upload`
- `GET /api/v1/files`
- `GET /api/v1/files/download`
- `DELETE /api/v1/files`
- `GET /api/v1/files/presign`

Compatibility endpoints:

- `POST /common/files/upload`
- `GET /common/files/list`
- `GET /common/files/download`
- `DELETE /common/files/remove`
- `GET /common/files/presign`
- `POST /manage/files`
- `GET /manage/files/list`
- `GET /manage/files/download`
- `DELETE /manage/files/{id}`

## `dt-legacy-dotnet-svc`

Anti-corruption layer around `../../web-energy/api`.

Responsibilities:

- Isolate Java services from `.NET` response envelopes and endpoint shapes.
- Map old `ApiResponseDto` responses to Java result types.
- Delegate v1 execution for PLC and OCPP.
- Contain no new business rules.

Initial endpoints:

- `GET /api/v1/legacy-dotnet/status`
- OCPP compatibility command routes under `/ocpp/*`
