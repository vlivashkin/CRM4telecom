/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import java.util.List;
import ejb.jpa.Customers;
import ejb.jpa.Products;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Ilya Vasilyev
 */
@Local
public interface CustomerManagerInterface {
    
    List<Object> getItems(String databaseName);
    
    List<Customers> getCustomersList();
    
    List<Products> getProductsList();
    
    public void merge(Object object);
    
    public String addCustomer();
    
    public String addProduct(Long customerID, String productName);
    
    public String removeProduct(Long customerID, String productName);
    
    public double getBalance(Long customerID);
    
    Map<Long, String> getStatuses();
    
}
