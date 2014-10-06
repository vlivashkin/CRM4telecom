/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.jpa.Customer;
import ejb.jpa.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless
public class CustomerManager implements CustomerManagerInterface{

    @PersistenceContext
    private EntityManager em;
    
    //Names of Data Bases
    private String customersBase = "Customer";
    private String productsBase = "Product";
    //
    
    
    @Override
    public List<Object> getItems(String databaseName) {
        return em.createQuery("SELECT c FROM " + databaseName + " c").getResultList();
    }

    @Override
    public void merge(Object object) {
        em.merge(object);
    }

    @Override
    public String addCustomer() {
        Customer customer = new Customer();
        try {
            this.merge(customer);
            return "ok";
        } catch (Exception e){
            return("no");
        }    
    }

    @Override
    public String addProduct(Long customerID, Long productID) {
        Customer c = this.getCustomer(customerID);
        if (c == null){
            return "err";
        }
        
        List newList = c.getProductsList();
        for(Product p : this.getProductsList()){
            if (p.getProductId() == productID){
                newList.add(p);
                this.merge(c);
                return "suc";
            }
        }
        return "err";
    }

    @Override
    public String removeProduct(Long customerID, Long productID) {
        Customer c = this.getCustomer(customerID);
        if (c == null){
            return "err";
        }
        
        List newList = c.getProductsList();
        for(Product p : this.getProductsList()){
            if (p.getProductId() == productID){
                newList.remove(p);
                this.merge(c);
                return "suc";
            }
        }
        return "err";
    }

    @Override
    public double getBalance(Long customerID) {
        Query query = em.createQuery("SELECT c FROM " + customersBase + " c" + "WHERE c.customerId = :customerID").setParameter("customerID", customerID);
        List<Customer> resultList = query.getResultList();
        Customer customer = resultList.get(0);
        return customer.getBalance();
    }

    @Override
    public Map<Long, String> getStatuses() {
        List<Customer> list = this.getCustomersList();
        Map<Long, String> resultMap = new HashMap<Long, String>();
        
        for(Customer c : list){
            resultMap.put(c.getCustomerId(), c.getStatus());
        }
        return resultMap;
    }

    @Override
    public List<Customer> getCustomersList() {
        return em.createQuery("SELECT c FROM " + customersBase + " c").getResultList();
    }

    @Override
    public List<Product> getProductsList() {
        return em.createQuery("SELECT c FROM " + productsBase + " c").getResultList();
    }

    @Override
    public Customer getCustomer(Long customerID) {
        for (Customer c : this.getCustomersList()){
            if (c.getCustomerId() == customerID)
                return c;
        }
        return null;
    }

    
    
}
