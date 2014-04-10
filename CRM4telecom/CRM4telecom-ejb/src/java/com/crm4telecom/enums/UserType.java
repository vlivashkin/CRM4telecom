package com.crm4telecom.enums;

public enum UserType {

    ADMIN("Admin"),
    USER("User");

    private String label;

    private UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
