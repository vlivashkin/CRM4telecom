package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.StaticIp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IpFilling extends FillingDatabase implements IpFillingLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void getDataAndFill(Customer customer) {
        String sqlQuery = "SELECT i FROM StaticIp i WHERE i.customerId IS NULL";
        Query query = em.createQuery(sqlQuery);
        List<StaticIp> ipList = query.getResultList();
        if (ipList.size() > 0) {
            StaticIp ip = ipList.get(0);
            ip.setCustomerId(customer);
            em.persist(ip);
            System.out.println("New ip: " + ip.getIp());
        } else {
            System.out.println("No free ip's!");
        }
    }
}
