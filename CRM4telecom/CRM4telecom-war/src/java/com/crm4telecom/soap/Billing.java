package com.crm4telecom.soap;

import com.crm4telecom.ejb.CustomerManagerLocal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "services")
public class Billing {

    @EJB
    private CustomerManagerLocal cm;

    @WebMethod(operationName = "getBalance")
    public long getBalance(@WebParam(name = "id") long id) {

        return cm.getCustomer(id).getBalance();
    }
}
