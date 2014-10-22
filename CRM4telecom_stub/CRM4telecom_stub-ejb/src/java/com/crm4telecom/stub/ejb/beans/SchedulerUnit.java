package com.crm4telecom.stub.ejb.beans;

import com.crm4telecom.stub.beans.enums.CustomerStatus;
import com.crm4telecom.stub.ejb.jpa.Customer;
import com.crm4telecom.stub.ejb.jpa.Product;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@Startup
public class SchedulerUnit implements SchedulerUnitInterface {

    
    
    @EJB
    CustomerManagerInterface cm;
    
    long startDelay;
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    
    final Runnable checker = new Runnable() {
        
        @Override
        public void run() {
           System.out.println("******** Sheduler block started"); 
           withdrawMoney(checkStatuses());
           System.out.println("******** Withdraw succeeded");
        }
    };

    //ScheduledFuture<?> withdrawHandle = scheduler.scheduleAtFixedRate(checker, 2, 2, SECONDS);

    public SchedulerUnit() {
        startDelay = (24 - Calendar.HOUR_OF_DAY) * 60 - Calendar.MINUTE;
        //scheduler.scheduleAtFixedRate(checker, 2, 2, SECONDS);
        scheduler.scheduleAtFixedRate(checker, startDelay, 24*60, MINUTES);
    } 
    @Override
    public void withdrawMoney(Map<Long, CustomerStatus> map) {
        Double cash = 0.0;
        System.out.println("******** Start withdrawMoney");
        for(Entry<Long, CustomerStatus> elem : map.entrySet()){
            Customer target = cm.getCustomer(elem.getKey());
            cash = 0.0;
            for(Product p : target.getProductsList()){
                cash += p.getMonthlyPrice()/30;
                System.out.println("******** Customer " + target.getCustomerId() + " Cashes calculated " + cash);
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
        
        System.out.println("******** checkStatuses has got map");
        
        for (Entry<Long, CustomerStatus> elem : statusMap.entrySet()){
            if(elem.getValue().equals(CustomerStatus.BLOCKED)){
                statusMap.remove(elem.getKey());
            }
            if(elem.getValue().equals(CustomerStatus.UNPLUGGED)){
                statusMap.remove(elem.getKey());
            }
            System.out.println("******** Customer " + elem.getKey() + " checked");
        }
        System.out.println("******** Statuses checked");
        return statusMap;
    }

    
}