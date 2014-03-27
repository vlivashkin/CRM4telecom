package com.crm4telecom.enums;

public enum IpStatus {

    ACTIVE("Active"),
    BLOCKED("Blocked"),
    UNPLUGGED("Unplugged");

    private final String label;

    private IpStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
