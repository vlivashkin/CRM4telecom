/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.jpa.Customers;
import ejb.jpa.Products;
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
    private String customersBase = "Customers";
    private String productsBase = "Products";
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
        Customers customer = new Customers();
        try {
            this.merge(customer);
            return "ok";
        } catch (Exception e){
            return("no");
        }    
    }

    @Override
    public String addProduct(Long customerID, Long productID) {
        Customers c = this.getCustomer(customerID);
        if (c == null){
            return "err";
        }
        
        List newList = c.getProductsList();
        for(Products p : this.getProductsList()){
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
        Customers c = this.getCustomer(customerID);
        if (c == null){
            return "err";
        }
        
        List newList = c.getProductsList();
        for(Products p : this.getProductsList()){
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
        List<Customers> resultList = query.getResultList();
        Customers customer = resultList.get(0);
        return customer.getBalance();
    }

    @Override
    public Map<Long, CustomerStatus> getStatuses() {
        List<Customers> list = this.getCustomersList();
        Map<Long, CustomerStatus> resultMap = new HashMap<Long, CustomerStatus>();
        
        for(Customers c : list){
            resultMap.put(c.getCustomerId(), c.getStatus());
        }
        return resultMap;
    }

    @Override
    public List<Customers> getCustomersList() {
        return em.createQuery("SELECT c FROM " + customersBase + " c").getResultList();
    }

    @Override
    public List<Products> getProductsList() {
        return em.createQuery("SELECT c FROM " + productsBase + " c").getResultList();
    }

    @Override
    public Customers getCustomer(Long customerID) {
        for (Customers c : this.getCustomersList()){
            if (c.getCustomerId() == customerID)
                return c;
        }
        return null;
    }   

    @Override
    public void withdraw(Long customerID, Double cash) {
        Customers target = getCustomer(customerID);
        target.setBalance(target.getBalance() - cash);
        this.merge(target);
    }

    @Override
    public void setStatus(Long customerID, CustomerStatus status) {
        Customers target = getCustomer(customerID);
        target.setStatus(status);
        this.merge(target);
    }
    
}
