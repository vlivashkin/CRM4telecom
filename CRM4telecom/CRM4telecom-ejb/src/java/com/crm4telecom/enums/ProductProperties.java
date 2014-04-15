package com.crm4telecom.enums;

public enum ProductProperties {

    NONE("None"),
    IP("IP"),
    PHONE("Phone");

    private final String label;

    private ProductProperties(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
