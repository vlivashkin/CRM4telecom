package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class GetManager implements GetManagerLocal, GetManagerRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product getProduct(String product) {
        if (product != null) {
            String sqlQuery = "SELECT u FROM Product u WHERE u.name = :name";
            return create(sqlQuery, product);
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public List<String> getProductList() {
        String sqlQuery = "SELECT u.name FROM Product u";
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
            throw new NullPointerException();
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
