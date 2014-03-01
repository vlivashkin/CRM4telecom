package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CustomerManager implements CustomerManagerLocal {

    @PersistenceContext
    private EntityManager em;
        
    @Override
    public Customer createCustomer (String firstName, String lastName, String email, String street, Long building, Long flat, String cardNumber, Date cardExpData, Long balance) {
        Customer customer = new Customer();
        customer = fillCustomer(customer, firstName, lastName, email, street, building, flat, cardNumber, cardExpData, balance);
        em.persist(customer);
        
        return customer;
    }

    @Override
    public void modifyCustomer(Customer customer) {
        em.merge(customer);
    }
    
    @Override
    public Customer modifyCustomer(Long customerId, String firstName, String lastName, String email, String street, Long building, Long flat, String cardNumber, Date cardExpData, Long balance) {
        Customer customer = em.find(Customer.class, customerId); 
        if (customer == null)
            throw new NoSuchElementException();
        customer = fillCustomer(customer, firstName, lastName, email, street, building, flat, cardNumber, cardExpData, balance);
        em.merge(customer);
        
        return customer;
    }
    
    @Override
    public Customer getCustomer(Long customerId) {
        Customer customer = em.find(Customer.class, customerId);

        return customer;
    }
    
    @Override
    public List<Customer> getCustomerList() {    
        return em.createQuery("SELECT c FROM Customer c").getResultList();
    }
    
    @Override
    public List<Customer> getCustomerList(String order) {    
        return em.createQuery("SELECT c FROM Customer c ORDER BY :order", Customer.class).setParameter("order", order).getResultList();
    }
    
    private Customer fillCustomer(Customer customer, String firstName, String lastName, String email, String street, Long building, Long flat, String cardNumber, Date cardExpData, Long balance) {
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setStreet(street);
        customer.setBuilding(building);
        customer.setFlat(flat);
        customer.setCardNumber(cardNumber);
        customer.setCardExpData(cardExpData);
        customer.setBalance(balance);
        
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters) {
        String sqlQuery = "SELECT c FROM Customer c";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " c." + filterProperty + " like \'" + filterValue + "%\' AND";
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        if (sortField != null && !"".equals(sortField))
            sqlQuery += " ORDER BY c." + sortField;
        if ("DESCENDING".endsWith(sortOrder))
            sqlQuery += " DESC";

        Query query = em.createQuery(sqlQuery, Customer.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long getCustomersCount(Map<String, String> filters) {
        String sqlQuery = "SELECT COUNT(c) FROM Customer c";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " c." + filterProperty + " like \'" + filterValue + "%\' AND";
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        Query query = em.createQuery(sqlQuery, Customer.class);
        return (Long)query.getSingleResult();
   }
}
