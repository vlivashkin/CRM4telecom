package com.crm4telecom.soap;

public enum Result {

    ACCEPT("ACCEPT"),
    DECLINE("DECLINE"),
    CLIENT_NOT_FOUND("CLIENT_NOT_FOUND");

    private final String label;

    private Result(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
