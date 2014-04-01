package com.crm4telecom.enums;

public enum OrderStatus {

    NEW("New", "bg-info"),
    OPENED("In work", "bg-success"),
    CLOSED("Closed", "bg-info"),
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
