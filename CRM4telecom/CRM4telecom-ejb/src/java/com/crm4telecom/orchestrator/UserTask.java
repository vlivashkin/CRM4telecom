package com.crm4telecom.orchestrator;

public class UserTask extends Task {

    public UserTask(String label) {
        super(label);
    }

    @Override
    public TaskType getType() {
        return (TaskType.USER_TASK);
    }
}
