package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;
import javax.ejb.Local;

@Local
public interface PhoneFillingLocal {
    public void fillData(Customer customer);
}
