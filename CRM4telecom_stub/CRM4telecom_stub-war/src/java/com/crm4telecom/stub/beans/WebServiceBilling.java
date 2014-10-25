package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.ejb.beans.CustomerManagerInterface;
import java.util.Map;
import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(serviceName = "services", portName = "BillingPort", endpointInterface = "com.crm4telecom.soap.BillingWebService", targetNamespace = "http://soap.crm4telecom.com/", wsdlLocation = "WEB-INF/wsdl/WebServiceBilling/schema.wsdl")
public class WebServiceBilling {

    @EJB
    CustomerManagerInterface cm;

    public Boolean addMoney(double cash, long id) {
        return true;
    }

    public double getBalance(long id) {
        return cm.getBalance(id);
    }

    public Boolean addCustomer(Long customerID, Double balance, String status) {
        return cm.addCustomer(customerID, balance, status);
    }

    public Boolean removeProduct(java.lang.Long customerID, java.lang.String productName) {
        return cm.removeProduct(customerID, customerID);
    }

    public Boolean withdraw(double cash, long id) {
        try {
            cm.withdraw(id, cash);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public Boolean addProduct(java.lang.Long customerID, java.lang.String productName) {
        return cm.addProduct(customerID, customerID);
    }

    public Map<Long, com.crm4telecom.stub.beans.enums.CustomerStatus> getStatuses() {
        return cm.getStatuses();
    }

}
