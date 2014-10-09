/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import java.util.List;
import ejb.jpa.Customer;
import ejb.jpa.Product;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Ilya Vasilyev
 */
@Local
public interface CustomerManagerInterface {
    
    List<Object> getItems(String databaseName);
    
    List<Customer> getCustomersList();
    
    List<Product> getProductsList();
    
    Customer getCustomer(Long customerID);
    
    public void merge(Object object);
    
    public String addCustomer();
    
    public void withdraw(Long customerID, Double cash);
    
    public void setStatus(Long customerID, CustomerStatus status);
    
    public String addProduct(Long customerID, Long productID);
    
    public String removeProduct(Long customerID, Long productID);
    
    public double getBalance(Long customerID);
    
    Map<Long, CustomerStatus> getStatuses();

    public void setCustomers(List<Customer> customers);
    
}
