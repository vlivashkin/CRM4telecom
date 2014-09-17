package com.crm4telecom.stub.enums;

public enum CustomerStatus {

    ACTIVE("Active", "bg-success"),
    BLOCKED("Blocked", "bg-warning"),
    UNPLUGGED("Unplugged", "bg-danger");

    private final String label;
    private final String color;

    private CustomerStatus(String label, String color) {
        this.label = label;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }
    
    public String getColor() {
        return color;
    }
}
