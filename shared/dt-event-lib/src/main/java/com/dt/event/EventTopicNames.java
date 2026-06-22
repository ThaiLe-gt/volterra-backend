package com.dt.event;

public final class EventTopicNames {
    public static final String OPERATION_SITE_EVENTS = "dt.operation.site-events";
    public static final String OPERATION_DEVICE_EVENTS = "dt.operation.device-events";
    public static final String TELEMETRY_RAW_EVENTS = "dt.telemetry.raw-events";
    public static final String TELEMETRY_NORMALIZED_EVENTS = "dt.telemetry.normalized-events";
    public static final String COMMAND_EVENTS = "dt.command.events";
    public static final String ALARM_EVENTS = "dt.alarm.events";
    public static final String ENERGY_EV_EVENTS = "dt.energy.ev-events";
    public static final String ENERGY_BESS_EVENTS = "dt.energy.bess-events";
    public static final String OCPP_EVENTS = "dt.ocpp.events";
    public static final String AUDIT_EVENTS = "dt.audit.events";

    private EventTopicNames() {
    }

    public static String deadLetter(String topic) {
        return topic + ".dlq";
    }
}
