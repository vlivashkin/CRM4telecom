package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.jpa.Customers;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CustomerSessionBeanLocal {

    List<Customers> getCustomers();

    public void merge(Customers customer);
    
}
