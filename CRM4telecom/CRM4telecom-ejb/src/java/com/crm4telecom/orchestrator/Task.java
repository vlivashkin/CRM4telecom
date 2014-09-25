package com.crm4telecom.orchestrator;

import java.util.HashMap;

public abstract class Task {

    private String label;
    private HashMap<String, String> parameters;

    public Task(String label) {
        this.label = label;
    }

    public TaskType getType() {
        return null;
    }

    public boolean run() {
        return false;
    }

    public String getLabel() {
        return label;
    }

}
