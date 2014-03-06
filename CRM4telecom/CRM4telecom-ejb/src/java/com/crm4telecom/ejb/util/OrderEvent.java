package com.crm4telecom.ejb.util;

public enum OrderEvent {

    CREATED("Created"),
    SENT_TO_TECH_SUPPORT("Sent to technical support"),
    ENGINEER_APPOINTED("Engineer appointed"),
    DELAY("Delay deadlines"),
    READY("Ready"),
    DONE("Done"),
    CANCELLED("Cancelled");

    private final String label;

    private OrderEvent(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
