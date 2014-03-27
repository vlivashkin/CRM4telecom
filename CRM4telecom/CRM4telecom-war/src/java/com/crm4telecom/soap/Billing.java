package com.crm4telecom.soap;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "services")
public class Billing {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getBalance")
    public long getBalance(@WebParam(name = "id") long id) {
        //TODO write your implementation code here:
        return 6;
    }
}
