package com.crm4telecom.orchestrator;

import java.util.HashMap;

public class AutoTask extends Task {

    public AutoTask(String label) {
        super(label);

    }
    
    @Override
    public TaskType getType(){
        return(TaskType.AUTO_TASK);
    }

    @Override
    public  boolean run() {
        //TODO logic
        return false;
        
    }

}
