/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.beans.enums.CustomerStatus;
import com.crm4telecom.stub.ejb.beans.CustomerManager;
import java.util.Map;
import javax.jws.WebService;

/**
 *
 * @author root
 */
@WebService(serviceName = "services", portName = "BillingPort", endpointInterface = "com.crm4telecom.soap.Billing", targetNamespace = "http://soap.crm4telecom.com/", wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/schema.wsdl")
public class NewWebServiceFromWSDL {

    CustomerManager cm; 
    
    public java.lang.String addMoney(double cash, long id) {
        //TODO implement this method
        return "suc";
    }

    public double getBalance(long id) {
        //TODO implement this method
        return cm.getBalance(id);
    }

    public java.lang.String addCustomer(java.lang.String lastName, java.lang.String name) {
        //TODO implement this method
        return cm.addCustomer();
    }

    public java.lang.String removeProduct(java.lang.Long customerID, java.lang.String productName) {
        //TODO implement this method
        return cm.removeProduct(customerID, customerID);
    }

    public java.lang.String withdraw(double cash, long id) {
        //TODO implement this method
        try{
            cm.withdraw(id, cash);
            return "suc";
        } catch (Throwable e){
            return "err";
        }
    }

    public java.lang.String addProduct(java.lang.Long customerID, java.lang.String productName) {
        //TODO implement this method
        return cm.addProduct(customerID, customerID);
    }

    public Map<Long, CustomerStatus> getStatuses() {
        //TODO implement this method
        return cm.getStatuses();
    }
    
}
