/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.StaticIp;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IpManager implements IpManagerLocal {

  //  @PersistenceContext
  //  private EntityManager em;

    private static final String PERSISTENCE_UNIT_NAME = "CRM4telecom-ejbPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    @Override
    public void getFreeIp(Long customerId) {
        EntityManager em = factory.createEntityManager();
        String sqlQuery = "SELECT i FROM StaticIp i WHERE i.customerId IS NULL ";
        System.out.println(em == null);
        Query query = em.createQuery(sqlQuery);
        String ip;
        StaticIp s;
        System.out.println(query.getResultList().size());
        if (query.getResultList().size() > 0) {
            s = (StaticIp) query.getResultList().get(0);
            s.setCustomerId(em.find(Customer.class, customerId));
            em.persist(s);
        }
        em.close();
        
    }

}
