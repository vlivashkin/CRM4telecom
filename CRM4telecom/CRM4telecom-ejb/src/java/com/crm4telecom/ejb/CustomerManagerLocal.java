package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Market;
import com.crm4telecom.jpa.MarketsCustomers;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface CustomerManagerLocal {

    void createCustomer(Customer customer);

    void modifyCustomer(Customer customer);

    void addMarket(MarketsCustomers mc);
    
    List<Market> getMarkets(Customer customer);

    List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters, Map<String, List<String>> parametrs);

    Customer getCustomer(Long customerId);

    Long getCustomersCount();

    Long getCustomersCount(Map<String, String> filters, Map<String, List<String>> parametrs);

    void persist(Customer c);
    
    Customer find(long customerId);
}
