package com.crm4telecom.ejb.filling;

import com.crm4telecom.enums.IpStatus;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.StaticIp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class IpFilling extends FillingDatabase implements IpFillingRemote {

    private final Logger logger = LoggerFactory.getLogger(IpFilling.class);

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
            ip.setStatus(IpStatus.RESEREVED);
            em.merge(ip);
            em.flush();
            logger.info("Customer : " + customer + " now resereve ip address : " + ip.getIp());
            return true;
        } else {
            logger.warn("All ip adresses is locked, so customer : " + customer + " can't get new ip address");
            return false;
        }
    }

    @Override
    protected Boolean getDataAndActivate(Customer customer) {
        String sqlQuery = "SELECT i FROM StaticIp i WHERE i.customerId = :customer";
        Query query = em.createQuery(sqlQuery).setParameter("customer", customer);
        logger.info("Query made for " + customer);
        List<StaticIp> ipList = query.getResultList();
        if (ipList.size() > 0) {
            StaticIp ip = ipList.get(0);
            ip.setStatus(IpStatus.ACTIVE);
            em.merge(ip);
            em.flush();
            logger.info("Customer : " + customer + " now get ip address : " + ip.getIp());
            return true;
        } else {
            logger.warn("All ip adresses is locked, so customer : " + customer + " can't get new ip address");
            return false;
        }
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
            logger.info("Customer : " + customer + " now free ip address : " + ip.getIp());
            return true;
        } else {
            logger.warn("No ip of " + customer);
            return false;
        }
    }
}
