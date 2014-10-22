package com.crm4telecom.stub.ejb.beans;

import com.crm4telecom.stub.beans.enums.CustomerStatus;
import java.util.Map;

public interface SchedulerUnitInterface {
    
    public void withdrawMoney(Map<Long, CustomerStatus> map);
    
    public Map<Long, CustomerStatus> checkStatuses();
 
}