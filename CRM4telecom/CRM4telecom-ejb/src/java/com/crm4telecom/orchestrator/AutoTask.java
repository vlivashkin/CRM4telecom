package com.crm4telecom.orchestrator;

public abstract class AutoTask extends Task {
    public AutoTask(String label) {
        super(label);
    }

    @Override
    public TaskType getType() {
        return (TaskType.AUTO_TASK);
    }
}
