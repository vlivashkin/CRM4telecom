package com.crm4telecom.stub.ejb.beans;

import com.crm4telecom.stub.beans.enums.CustomerStatus;
import com.crm4telecom.stub.ejb.jpa.Customer;
import com.crm4telecom.stub.ejb.jpa.Product;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.MINUTES;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class SchedulerUnit implements SchedulerUnitInterface {

    private final Logger logger = LoggerFactory.getLogger(SchedulerUnit.class);

    @EJB
    CustomerManagerInterface cm;

    long startDelay;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    final Runnable checker = new Runnable() {

        @Override
        public void run() {
            logger.info("******** Sheduler block started");
            withdrawMoney(checkStatuses());
            logger.info("******** Withdraw succeeded");
        }
    };

    //ScheduledFuture<?> withdrawHandle = scheduler.scheduleAtFixedRate(checker, 2, 2, SECONDS);
    public SchedulerUnit() {
        startDelay = (24 - Calendar.HOUR_OF_DAY) * 60 - Calendar.MINUTE;
        //scheduler.scheduleAtFixedRate(checker, 2, 2, SECONDS);
        scheduler.scheduleAtFixedRate(checker, startDelay, 24 * 60, MINUTES);
    }

    @Override
    public void withdrawMoney(Map<Long, CustomerStatus> map) {
        Double cash;
        logger.info("******** Start withdrawMoney");
        for (Entry<Long, CustomerStatus> elem : map.entrySet()) {
            Customer target = cm.getCustomer(elem.getKey());
            cash = 0.0;
            for (Product p : target.getProductsList()) {
                cash += p.getMonthlyPrice() / 30;
                logger.info("******** Customer " + target.getCustomerId() + " Cashes calculated " + cash);
            }
            cm.withdraw(elem.getKey(), cash);
            if (cm.getBalance(elem.getKey()) <= 0.0) {
                cm.setStatus(elem.getKey(), CustomerStatus.BLOCKED);
            }
        }
    }

    @Override
    public Map<Long, CustomerStatus> checkStatuses() {
        Map<Long, CustomerStatus> statusMap = cm.getStatuses();
        Set<Long> toBeRemoved = new HashSet<>();
        logger.info("******** checkStatuses has got map");

        for (Entry<Long, CustomerStatus> elem : statusMap.entrySet()) {
            if (elem.getValue().equals(CustomerStatus.BLOCKED)) {
                toBeRemoved.add(elem.getKey());
            }
            if (elem.getValue().equals(CustomerStatus.UNPLUGGED)) {
                toBeRemoved.add(elem.getKey());
            }
            logger.info("******** Customer " + elem.getKey() + " checked");
        }
        logger.info("******** All customers checked");
        for (Long id : toBeRemoved) {
            statusMap.remove(id);
        }
        logger.info("******** Statuses are ready");
        return statusMap;
    }

}
