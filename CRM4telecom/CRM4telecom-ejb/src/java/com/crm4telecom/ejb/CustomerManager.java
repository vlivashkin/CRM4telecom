package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.SearchQuery;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Market;
import com.crm4telecom.jpa.MarketsCustomers;
import java.util.ArrayList;
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
            persist(customer);

        } else {
            throw new IllegalArgumentException("Customer can't be null");
        }
    }

    @Override
    public void modifyCustomer(Customer customer) {
        em.merge(customer);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        Customer customer;
        if (customerId == null) {
            throw new IllegalArgumentException("CustomerId can't be null");
        }
        if (customerId > 0) {
            customer = find(customerId);
        } else {
            return null;
        }

        return customer;
    }

    @Override
    public void addMarket(MarketsCustomers mc) {
        em.persist(mc);
    }

    @Override
    public List<Market> getMarkets(Customer customer) {

        if (customer != null) {
            String sqlQuery = "SELECT c.marketsCustomersPK.marketId FROM Markets_Customers c WHERE c.marketsCustomersPK.customerId = :customerId";
            Query query = em.createQuery(sqlQuery).setParameter("customerId", customer.getCustomerId());

            List<Long> market_ids = query.getResultList();
            List<Market> markets = new ArrayList<Market>();
            for (Long temp : market_ids) {
                Query query2 = em.createQuery("SELECT u FROM Markets u WHERE u.marketId = :id").setParameter("id", temp);
                markets.add((Market) query2.getResultList().get(0));
            }
            return markets;
        } else {
            throw new IllegalArgumentException("Customer cannot be null");
        }
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

    @Override
    public void persist(Customer c) {
        em.persist(c);
    }

    @Override
    public Customer find(long customerId) {
        Customer customer = em.find(Customer.class, customerId);
        em.refresh(customer);
        return customer;
    }
}
