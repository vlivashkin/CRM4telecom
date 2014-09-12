package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.SearchQuery;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Market;
import com.crm4telecom.jpa.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

@Stateless
public class GetManager implements GetManagerLocal {

    private transient final Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProduct(Long productId) {
        if (productId != null) {
            if (productId > 0) {
                return em.find(Product.class, productId);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("ProductId can't be null");
        }
    }

    @Override
    public Product getProduct(String product) {
        if (product != null) {
            String sqlQuery = "SELECT u FROM Products u WHERE u.name = :name";
            return create(sqlQuery, product);
        } else {
            throw new IllegalArgumentException("Product can't be null");
        }
    }

    @Override
    public List<Product> getProductList() {
        String sqlQuery = "SELECT u FROM Products u";
        Query query = em.createQuery(sqlQuery);

        return query.getResultList();
    }

    @Override
    public Market getMarket(String market) {
        if (market != null) {
            String sqlQuery = "SELECT u FROM Market u WHERE u.name = :name";
            Query query = em.createQuery(sqlQuery).setParameter("name", market);
            return (Market) query.getResultList().get(0);
        } else {
            throw new IllegalArgumentException("Market can't be null");
        }
    }

    @Override
    public List<String> getMarketList() {
        String sqlQuery = "SELECT u.name FROM Market u";
        Query query = em.createQuery(sqlQuery);

        return query.getResultList();
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        if (employeeId != null) {
            if (employeeId > 0) {
                return find(employeeId);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("EmployeeId can't be null");
        }
    }

    @Override
    public List<String> completeCustomer(String rawCustomer) {
        return complete(Customer.class, rawCustomer);
    }

    @Override
    public List<String> completeEmployee(String rawEmployee) {
        return complete(Employee.class, rawEmployee);
    }

    private List<String> complete(Class clazz, String rawString) {
        if (rawString == null) {
            return null;
        }
        String raw = rawString.trim();

        if (raw.matches("^#?\\d+")) {
            List<String> list = new ArrayList<String>();
            if (raw.startsWith("#")) {
                raw = raw.substring(1);
            }
            Long id = Long.parseLong(raw);
            Object found = em.find(clazz, id);
            if (found != null) {
                list.add(found.toString());
            }
            return list;
        } else {
            String sqlQuery = SearchQuery.getCompleteQuery(clazz, rawString);
            Query query = em.createQuery(sqlQuery);
            query.setMaxResults(10);
            if (log.isInfoEnabled()) {
                log.info("Make query for autocomlete : " + sqlQuery);
            }
            return query.getResultList();
        }
    }

    @Override
    public Product create(String sqlQuery, String product) {
        Query query = em.createQuery(sqlQuery).setParameter("name", product);
        return (Product) query.getResultList().get(0);
    }

    @Override
    public Employee find(long employeeId) {
        return em.find(Employee.class, employeeId);
    }

}
