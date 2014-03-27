package com.crm4telecom.enums;

public enum OrderPriority {

    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    CRITICAL("Critical");
    
    private String label;
    
    private OrderPriority(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
}
