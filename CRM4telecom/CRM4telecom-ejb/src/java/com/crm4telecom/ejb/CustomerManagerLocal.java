package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.MarketsCustomers;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface CustomerManagerLocal {

    void createCustomer(Customer customer);

    void modifyCustomer(Customer customer);

    void addMarket(MarketsCustomers mc);

    List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters, Map<String, List<String>> parametrs);

    Long getCustomersCount();

    Long getCustomersCount(Map<String, Object> filters, Map<String, List<String>> parametrs);

    Customer getCustomer(Long customerId);
}
