package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;
import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface PhoneFillingRemote {

    public Boolean allocateItem(Customer customer);

    public Boolean freeItem(Customer customer);
}
