package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CustomerManagerLocal {
    Customer getCustomer(Long customerId);

    List<Customer> getCustomersList();
    
   void addCustomer(String firstName, String lastName, String email, String cardNumber, Date cardExpData, Long balance);

   void alterCustomer(Long customerId, String firstName, String lastName, String email, String cardNumber, Date cardExpData, Long balance);
}
