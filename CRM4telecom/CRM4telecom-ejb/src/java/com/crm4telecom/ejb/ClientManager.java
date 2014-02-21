package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientManager implements ClientManagerLocal {

    @PersistenceContext
    private EntityManager em;
        
    @Override
    public Customer getCustomer(int customerId) {
        Customer customer = em.find(Customer.class, customerId);

        return customer;
    }

    @Override
    public void alterCustomer(int customerId, String firstName, String lastName, String email, String cardNumber, Date cardExpData, int balance) {
        Customer customer = em.find(Customer.class, customerId); 
        if (customer == null)
            throw new NoSuchElementException();
        else {
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setCardNumber(cardNumber);
            customer.setCardExpData(cardExpData);
            customer.setBalance(BigInteger.valueOf(balance));
            
            em.merge(customer);
        }
    }
    
    
    @Override
    public void addCustomer(String firstName, String lastName, String email, String cardNumber, Date cardExpData, int balance) {
        Customer customer = new Customer();
        
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setCardNumber(cardNumber);
        customer.setCardExpData(cardExpData);
        customer.setBalance(BigInteger.valueOf(balance));

        em.persist(customer);
    }

    @Override
    public List<Customer> getCustomersList() {      
        return em.createQuery("select * from Customer", Customer.class).getResultList();
    }
    
    
}
