package com.crm4telecom.orchestrator;

import java.util.HashMap;

public abstract class Task {

    public String label;
    public HashMap<String, String> parameters;
    public OrderStatus doneStatus;

    public Task(String label) {
        this.label = label;
    }
    public TaskType getType(){
        return null;
    }
    public boolean run(){
        return false;
    }
}
