package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.jpa.Product;
import com.crm4telecom.jpa.StaticIp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class GetManager implements GetManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void getFreeIp(Long customerId) {
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
    }

    @Override
    public Product getProduct(Long productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public Product getProduct(String product) {
        String sqlQuery = "SELECT u FROM Product u WHERE u.name = :name";
        Query query = em.createQuery(sqlQuery).setParameter("name", product);

        return (Product) query.getResultList().get(0);
    }

    @Override
    public List<String> getProductList() {
        String sqlQuery = "SELECT u.name FROM Product u";
        Query query = em.createQuery(sqlQuery);

        return query.getResultList();
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        return em.find(Employee.class, employeeId);
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

        if (raw.matches("^#?\\d+$")) {
            List<String> list = new ArrayList<>();
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
            String[] split = raw.split(" ");
            String sqlQuery
                    = "SELECT c "
                    + "FROM " + clazz.getName() + " c "
                    + "WHERE (LOWER(c.firstName) REGEXP :first "
                    + "or LOWER(c.lastName) REGEXP :first)";
            if (split.length > 1) {
                sqlQuery
                        += " and (LOWER(c.firstName) REGEXP :second "
                        + "or LOWER(c.lastName)  REGEXP :second)";
            }
            Query query = em.createQuery(sqlQuery);
            query.setParameter("first", split[0].toLowerCase());
            if (split.length > 1) {
                query.setParameter("second", split[1].toLowerCase());
            }
            query.setMaxResults(10);

            return query.getResultList();
        }
    }

}
