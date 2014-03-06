package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface CustomerManagerLocal {

    void createCustomer(Customer customer);

    void modifyCustomer(Customer customer);

    Customer getCustomer(Long customerId);

    List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters);

    Long getCustomersCount();

    Long getCustomersCount(Map<String, String> filters);
    
    List<Customer> search(Map<String, String> parametr);
}
