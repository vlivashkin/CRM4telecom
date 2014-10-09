package com.crm4telecom.stub.ejb.beans;

import com.crm4telecom.stub.beans.enums.CustomerStatus;
import com.crm4telecom.stub.ejb.jpa.Customer;
import com.crm4telecom.stub.ejb.jpa.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.HOURS;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class SchedulerUnit implements SchedulerUnitInterface {

    CustomerManager cm = new CustomerManager();
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    final Runnable checker = new Runnable() {
        @Override
        public void run() {
           withdrawMoney(checkStatuses());
        }
    };
    
    final ScheduledFuture<?> withdrawHandle = scheduler.scheduleAtFixedRate(checker, 8, 8, HOURS);
    
    @Override
    public void withdrawMoney(Map<Long, CustomerStatus> map) {
        Double cash = 0.0;
        for(Entry<Long, CustomerStatus> elem : map.entrySet()){
            Customer target = cm.getCustomer(elem.getKey());
            for(Product p : target.getProductsList()){
                cash += p.getMonthlyPrice();
            }
            cm.withdraw(elem.getKey(), cash);
            if(cm.getBalance(elem.getKey()) <= 0.0){
                cm.setStatus(elem.getKey(), CustomerStatus.BLOCKED);
            }
        }
    }

    @Override
    public Map checkStatuses() {
        Map<Long, CustomerStatus> statusMap = cm.getStatuses();
        
        for (Entry<Long, CustomerStatus> elem : statusMap.entrySet()){
            if(elem.getValue().equals(CustomerStatus.BLOCKED)){
                statusMap.remove(elem.getKey());
            }
            if(elem.getValue().equals(CustomerStatus.UNPLUGGED)){
                statusMap.remove(elem.getKey());
            }
        }
        return statusMap;
    }

    
}