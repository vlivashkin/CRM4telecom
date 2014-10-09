package com.crm4telecom.orchestrator;

import com.crm4telecom.ejb.OrderManager;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.filling.IpFilling;
import com.crm4telecom.ejb.filling.PhoneFilling;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

public class AutoTask extends Task {

    
    public AutoTask(String label) {
        super(label);

    }

    public TaskType getType() {
        return (TaskType.AUTO_TASK);
    }

    @Override
    public boolean run() {
        //TODO logic
        return false;

    }

}
