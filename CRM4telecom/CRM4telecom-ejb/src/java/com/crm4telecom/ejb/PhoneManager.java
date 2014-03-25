package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.PhoneNumber;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Stateless
public class PhoneManager implements PhoneManagerLocal {

    private static final String PERSISTENCE_UNIT_NAME = "CRM4telecom-ejbPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    
    @Override
    public void getFreePhone(Long customerId) {
        EntityManager em = factory.createEntityManager();
        String sqlQuery = "SELECT i FROM PhoneNumber i WHERE i.customerId IS NULL ";
         Query query = em.createQuery(sqlQuery);
         if (query.getResultList().size() > 0 ){ 
         PhoneNumber p = (PhoneNumber)query.getResultList().get(0);
         p.setCustomerId(em.find(Customer.class, customerId));
         em.persist(p);
         }
         em.close();
    }
    
}
