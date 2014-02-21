package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ClientManagerLocal {

    Customer getCustomer(int customerId);

    void alterCustomer(int customerId, String firstName, String lastName, String email, String cardNumber, Date cardExpData, int balance);

    void addCustomer(String firstName, String lastName, String email, String cardNumber, Date cardExpData, int balance);

    List<Customer> getCustomersList();

}
