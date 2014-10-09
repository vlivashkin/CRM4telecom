package com.crm4telecom.ejb.filling;

import com.crm4telecom.enums.IpStatus;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.StaticIp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

@Stateless
public class IpFilling extends FillingDatabase implements IpFillingLocal,IpFillingRemote {

    private transient final Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    protected Boolean getDataAndAlloc(Customer customer) {
        String sqlQuery = "SELECT i FROM StaticIp i WHERE i.customerId IS NULL";
        Query query = em.createQuery(sqlQuery);
        List<StaticIp> ipList = query.getResultList();
        if (ipList.size() > 0) {
            StaticIp ip = ipList.get(0);
            ip.setCustomerId(customer);
            ip.setStatus(IpStatus.ACTIVE);
            em.merge(ip);
            em.flush();

            if (log.isInfoEnabled()) {
                log.info("Customer : " + customer + " now get ip address : " + ip.getIp());
                return true;
            }
        } else {
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("All ip adresses is locked, so customer : " + customer + " can't get new ip address");
                return false;
            }
        }
        return false;
    }

    @Override
    protected Boolean getDataAndFree(Customer customer) {
        String sqlQuery = "SELECT i FROM StaticIp i WHERE i.customerId = :customer";
        Query query = em.createQuery(sqlQuery).setParameter("customer", customer);
        List<StaticIp> ipList = query.getResultList();
        if (ipList.size() > 0) {
            StaticIp ip = ipList.get(0);
            ip.setCustomerId(null);
            ip.setStatus(IpStatus.UNPLUGGED);
            em.merge(ip);
            em.flush();

            if (log.isInfoEnabled()) {
                log.info("Customer : " + customer + " now free ip address : " + ip.getIp());
                return true;
            }
        } else {
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("No ip of " + customer);
                return false;
            }
        }
        return false;
    }
}
