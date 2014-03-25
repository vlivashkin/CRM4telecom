package com.crm4telecom.ejb;

import javax.ejb.Local;

@Local
public interface PhoneManagerLocal {
    void getFreePhone(Long customerId);
}
