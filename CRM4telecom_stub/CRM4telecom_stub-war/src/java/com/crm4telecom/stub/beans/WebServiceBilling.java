/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.ejb.beans.CustomerManagerInterface;
import java.util.Map;
import javax.ejb.EJB;
import javax.jws.WebService;

/**
 *
 * @author root
 */
@WebService(serviceName = "services", portName = "BillingPort", endpointInterface = "com.crm4telecom.soap.BillingWebService", targetNamespace = "http://soap.crm4telecom.com/", wsdlLocation = "WEB-INF/wsdl/WebServiceBilling/schema.wsdl")
public class WebServiceBilling {

    @EJB
    CustomerManagerInterface cm; 
    
    public Boolean addMoney(double cash, long id) {
        //TODO implement this method
        return true;
    }

    public double getBalance(long id) {
        //TODO implement this method
        return cm.getBalance(id);
    }

    public Boolean addCustomer(java.lang.String lastName, java.lang.String name) {
        //TODO implement this method
        return cm.addCustomer();
    }

    public Boolean removeProduct(java.lang.Long customerID, java.lang.String productName) {
        //TODO implement this method
        return cm.removeProduct(customerID, customerID);
    }

    public Boolean withdraw(double cash, long id) {
        //TODO implement this method
        try{
            cm.withdraw(id, cash);
            return true;
        } catch (Throwable e){
            return false;
        }
    }

    public Boolean addProduct(java.lang.Long customerID, java.lang.String productName) {
        //TODO implement this method
        return cm.addProduct(customerID, customerID);
    }

    public Map<Long, com.crm4telecom.stub.beans.enums.CustomerStatus> getStatuses() {
        //TODO implement this method
        return cm.getStatuses();
    }
    
}
