package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CustomerManagerLocal {
    Customer createCustomer(String firstName, String lastName, String email, String street, Long building, Long flat, String cardNumber, Date cardExpData, Long balance);
    
    void modifyCustomer(Customer customer);
    
    Customer modifyCustomer(Long customerId, String firstName, String lastName, String email, String street, Long building, Long flat, String cardNumber, Date cardExpData, Long balance);

    Customer getCustomer(Long customerId);

    List<Customer> getCustomerList();
    
    List<Customer> getCustomerList(String order);
}
