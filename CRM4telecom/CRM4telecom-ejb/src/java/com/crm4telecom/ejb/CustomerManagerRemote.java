
package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Market;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface CustomerManagerRemote {

    void createCustomer(Customer customer);

    void modifyCustomer(Customer customer);

    List<Market> getMarkets(Customer customer);

    List<Customer> getCustomersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters, Map<String, List<String>> parametrs);

    Customer getCustomer(Long customerId);

    Long getCustomersCount();

    Long getCustomersCount(Map<String, String> filters, Map<String, List<String>> parametrs);

    List<Customer> search(Map<String, String> parameter);

    void persist(Customer c);
}