package ejb.beans;

import java.util.Map;

public interface SchedulerUnitInterface {
    
    public void withdrawMoney(Map<Long, CustomerStatus> map);
    
    public Map checkStatuses();
 
}