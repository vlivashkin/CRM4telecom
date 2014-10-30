package com.crm4telecom.orchestrator;

public enum OrderStatus {

    NEW("New", "bg-info"),
    OPENED("In work", "bg-success"),
    CLOSED("Closed", "bg-info"),
    ERROR("Error", "bg-danger"),
    CANCELLED("Cancelled", "bg-danger");

    private final String label;
    private final String style;

    private OrderStatus(String label, String style) {
        this.label = label;
        this.style = style;
    }

    public String getLabel() {
        return label;
    }

    public String getColor() {
        return style;
    }
}
