package com.crm4telecom.ejb;

import javax.ejb.Local;

@Local
public interface IpManagerLocal {

    void getFreeIp(Long customerId);
}
