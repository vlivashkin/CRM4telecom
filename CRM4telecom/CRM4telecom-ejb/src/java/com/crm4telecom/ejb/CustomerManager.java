package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
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
    public List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT c FROM Customer c";
        if (!parametrs.isEmpty()) {
            sqlQuery += " WHERE";
            for (String paramProperty : parametrs.keySet()) {
                List<String> val = (List<String>) parametrs.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (String val1 : val) {
                        sqlQuery += " LOWER(c." + paramProperty + ") REGEXP LOWER('" + val1 + "') OR";
                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    sqlQuery += "   LOWER( c." + paramProperty + " ) REGEXP LOWER('" + val.get(0) + "')   AND";
                }
            }
        }
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += "  LOWER( c." + filterProperty + ") like LOWER( \'%" + filterValue + "%\')  AND";
            }
        }
        if (sqlQuery.endsWith("WHERE")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "WHERE".length());
        }
        if (sqlQuery.endsWith("AND")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "AND".length());
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
        System.out.println(sqlQuery);
        return query.getResultList();
    }

    @Override
    public Long getCustomersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Customer c";
        Query query = em.createQuery(sqlQuery, Customer.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCustomersCount(Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT COUNT(c) FROM Customer c";
        if (!parametrs.isEmpty()) {
            sqlQuery += " WHERE";
            for (String paramProperty : parametrs.keySet()) {
                List<String> val = (List<String>) parametrs.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (String val1 : val) {
                        sqlQuery += "  LOWER(c." + paramProperty + ") REGEXP LOWER('" + val1 + "') OR";
                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    sqlQuery += "   LOWER( c." + paramProperty + " ) REGEXP LOWER('" + val.get(0) + "')   AND";
                }
            }
        }
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += "   LOWER( c." + filterProperty + ") like LOWER( \'%" + filterValue + "%\' ) AND";
            }
        }
        if (sqlQuery.endsWith("WHERE")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "WHERE".length());
        }
        if (sqlQuery.endsWith("AND")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "AND".length());
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
                sqlQuery += "  LOWER(c." + pairs.getKey() + ") REGEXP LOWER('" + pairs.getValue() + "') AND";
                System.out.println(pairs.getKey() + " = " + pairs.getValue());
                it.remove();
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        return em.createQuery(sqlQuery).getResultList();
    }
}
