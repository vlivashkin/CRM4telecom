package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerManager implements CustomerManagerLocal {

    @PersistenceContext
    private EntityManager em;
        
    @Override
    public Customer getCustomer(Long customerId) {
        Customer customer = em.find(Customer.class, customerId);

        return customer;
    }
    
    @Override
    public List<Customer> getCustomersList() {      
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    @Override
    public void addCustomer(String firstName, String lastName, String email, String cardNumber, Date cardExpData, Long balance) {
        Customer customer = new Customer(firstName, lastName, email, cardNumber, cardExpData, balance);
        em.persist(customer);
    }

    @Override
    public void alterCustomer(Long customerId, String firstName, String lastName, String email, String cardNumber, Date cardExpData, Long balance) {
        Customer customer = em.find(Customer.class, customerId); 
        if (customer == null)
            throw new NoSuchElementException();
        else {
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setCardNumber(cardNumber);
            customer.setCardExpData(cardExpData);
            customer.setBalance(balance);
            
            em.merge(customer);
        }
    }
}
