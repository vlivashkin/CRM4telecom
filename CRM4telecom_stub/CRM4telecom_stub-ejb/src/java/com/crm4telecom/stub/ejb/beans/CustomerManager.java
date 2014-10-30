/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.stub.ejb.beans;

import com.crm4telecom.stub.beans.enums.CustomerStatus;
import com.crm4telecom.stub.ejb.jpa.Customer;
import com.crm4telecom.stub.ejb.jpa.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless
public class CustomerManager implements CustomerManagerInterface {

    @PersistenceContext
    private EntityManager em;

    //Names of Entities
    private final String customersEntity = "Customer";
    private final String productsEntity = "Product";
    //

    @Override
    public List<Object> getItems(String databaseName) {
        return em.createQuery("SELECT c FROM " + databaseName + " c").getResultList();
    }

    @Override
    public Boolean addCustomer(Long customerID, Double balance, String status) {
        Customer customer = new Customer();
        customer.setBalance(balance);
        customer.setCustomerId(customerID);
        customer.setStatus(CustomerStatus.valueOf(status));
        try {
            em.merge(customer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addProduct(Long customerID, Long productID) {
        Customer c = this.getCustomer(customerID);
        if (c == null) {
            return false;
        }

        List newList = c.getProductsList();
        for (Product p : this.getProductsList()) {
            if (p.getProductId().equals(productID)) {
                if (newList.contains(p)) {
                    return false;
                } else {
                    newList.add(p);
                    em.merge(c);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean removeProduct(Long customerID, Long productID) {
        Customer c = this.getCustomer(customerID);
        if (c == null) {
            return false;
        }

        List newList = c.getProductsList();
        for (Product p : this.getProductsList()) {
            if (p.getProductId().equals(productID)) {
                newList.remove(p);
                em.merge(c);
                return false;
            }
        }
        return false;
    }

    @Override
    public double getBalance(Long customerID) {
        Query query = em.createQuery("SELECT c FROM " + customersEntity + " c " + "WHERE c.customerId = :customerID").setParameter("customerID", customerID);
        List<Customer> resultList = query.getResultList();
        Customer customer = resultList.get(0);
        return customer.getBalance();
    }

    @Override
    public Map<Long, CustomerStatus> getStatuses() {
        List<Customer> list = this.getCustomersList();
        Map<Long, CustomerStatus> resultMap = new HashMap<>();

        for (Customer c : list) {
            resultMap.put(c.getCustomerId(), c.getStatus());
        }
        return resultMap;
    }

    @Override
    public List<Customer> getCustomersList() {
        return em.createQuery("SELECT c FROM " + customersEntity + " c").getResultList();
    }

    @Override
    public List<Product> getProductsList() {
        return em.createQuery("SELECT c FROM " + productsEntity + " c").getResultList();
    }

    @Override
    public Customer getCustomer(Long customerID) {
        for (Customer c : this.getCustomersList()) {
            if (c.getCustomerId().equals(customerID)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Boolean setCustomers(List<Customer> customers) {
        try {
            for (Customer c : customers) {
                em.merge(c);
            }
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Boolean withdraw(Long customerID, Double cash) {
        try {
            Customer target = getCustomer(customerID);
            if (target.getStatus().equals(CustomerStatus.ACTIVE)) {
                if (target.getBalance() <= 0.0) {
                    target.setStatus(CustomerStatus.BLOCKED);
                    return false;
                }
                if (target.getBalance() > cash) {
                    target.setBalance(target.getBalance() - cash);
                } else {
                    return false;
                }
                em.merge(target);
                em.flush();
                return true;
            } else {
                return false;
            }
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Boolean setStatus(Long customerID, CustomerStatus status) {
        try {
            Customer target = getCustomer(customerID);
            target.setStatus(status);
            em.merge(target);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

}
