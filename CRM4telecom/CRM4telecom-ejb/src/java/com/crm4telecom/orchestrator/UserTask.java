package com.crm4telecom.orchestrator;

import java.util.HashMap;

public class UserTask extends Task {

    public UserTask(String label) {
        super(label);

    }

    public TaskType getType() {
        return (TaskType.USER_TASK);
    }

    public boolean run() {
        //TODO logic
        return true;
    }

}
