package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.SearchQuery;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.MarketsCustomers;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

@Stateless
public class CustomerManager implements CustomerManagerLocal {

    private transient final Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createCustomer(Customer customer) {
        if (customer != null) {
            em.persist(customer);
        } else {
            throw new IllegalArgumentException("Customer can't be null");
        }
    }

    @Override
    public void modifyCustomer(Customer customer) {
        em.merge(customer);
    }

    @Override
    public void addMarket(MarketsCustomers mc) {
        em.persist(mc);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("CustomerId can't be null");
        }
        Customer customer = em.find(Customer.class, customerId);
        em.refresh(customer);
        return customer;
    }

    @Override
    public List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = SearchQuery.getSearchQuery("c FROM Customers c", parametrs, sortField, sortOrder);

        if (log.isInfoEnabled()) {
            log.info("Make query in Customers table " + sqlQuery);
        }

        Query query = em.createQuery(sqlQuery, Customer.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long getCustomersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Customers c";
        Query query = em.createQuery(sqlQuery, Customer.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCustomersCount(Map<String, Object> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = SearchQuery.getSearchQuery("COUNT(c) FROM Customers c", parametrs);

        Query query = em.createQuery(sqlQuery, Customer.class);
        return (Long) query.getSingleResult();
    }
}
