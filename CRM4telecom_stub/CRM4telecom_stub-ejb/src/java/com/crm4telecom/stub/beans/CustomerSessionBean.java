package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.jpa.Customers;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "CRM4telecom_stub-ejbPU")
    private EntityManager em;

    @Override
    public List<Customers> getCustomers() {
       return em.createNamedQuery("Customers.findAll").getResultList();
    }

    @Override
    public void merge(Customers customer) {
        em.merge(customer);
    }

}
