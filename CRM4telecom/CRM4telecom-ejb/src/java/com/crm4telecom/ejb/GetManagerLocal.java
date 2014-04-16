package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Market;
import com.crm4telecom.jpa.Product;
import java.util.List;
import javax.ejb.Local;

@Local
public interface GetManagerLocal {

    public Product getProduct(Long productId);
    
    public Product getProduct(String product);

    public List<String> getProductList();

    public Market getMarket(String market);
    
    public List<String> getMarketList();
            
    public Employee getEmployee(Long employeeId);

    public List<String> completeCustomer(String rawCustomer);

    public List<String> completeEmployee(String rawEmployee);
    
    Product create(String sqlQuery,String product);
    
    Employee find(long employeeId);
}
