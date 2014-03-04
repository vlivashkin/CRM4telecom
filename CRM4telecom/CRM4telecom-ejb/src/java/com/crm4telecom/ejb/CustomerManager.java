package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CustomerManager implements CustomerManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void modifyCustomer(Customer customer) {
        em.merge(customer);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        Customer customer = em.find(Customer.class, customerId);

        return customer;
    }

    @Override
    public List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters) {
        String sqlQuery = "SELECT c FROM Customer c";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " c." + filterProperty + " like \'" + filterValue + "%\' AND";
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        if (sortField != null && !"".equals(sortField)) {
            sqlQuery += " ORDER BY c." + sortField;
        }
        if ("DESCENDING".endsWith(sortOrder)) {
            sqlQuery += " DESC";
        }

        Query query = em.createQuery(sqlQuery, Customer.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long getCustomersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Customer c";

        Query query = em.createQuery(sqlQuery, Customer.class);
        return (Long) query.getSingleResult();
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
        return (Long) query.getSingleResult();
    }
    
    @Override
    public List<Customer> search(Map<String, String> parametr) {
        String sqlQuery = "SELECT c FROM Customer c     ";
        if (!parametr.isEmpty()) {
            sqlQuery += " WHERE";
            Iterator it = parametr.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                sqlQuery += " LOWER(c." + pairs.getKey() + ") REGEXP LOWER('" + pairs.getValue() + "') AND";
                System.out.println(pairs.getKey() + " = " + pairs.getValue());
                it.remove(); 
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        
        return em.createQuery(sqlQuery).getResultList();
    }

    
}
