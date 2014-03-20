package com.crm4telecom.enums;

public enum OrderEvent {

    SEND_TO_TECH_SUPPORT("Send to technical support"),
    ENGINEER_APPOINT("Engineer appoint"),
    SUCCESS("Close order"),
    CANCEL("Cancel order");

    private final String label;

    private OrderEvent(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
