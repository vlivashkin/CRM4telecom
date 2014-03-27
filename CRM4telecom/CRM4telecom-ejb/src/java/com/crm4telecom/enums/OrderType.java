package com.crm4telecom.enums;

public enum OrderType {

    CONNECT("Connect"),
    DISCONNECT("Disconnect");

    private String label;

    private OrderType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
