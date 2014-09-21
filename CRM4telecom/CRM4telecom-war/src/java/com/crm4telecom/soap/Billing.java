package com.crm4telecom.soap;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "services")
public class Billing {

    @EJB
    private CustomerManagerLocal cm;

    @WebMethod(operationName = "getBalance")
    public String getBalance(@WebParam(name = "id") long id) {
        Customer customer = cm.getCustomer(id);
        if (customer != null) {
            return customer.getBalance().toString();
        } else {
            return Result.CLIENT_NOT_FOUND.getLabel();
        }
    }

    @WebMethod(operationName = "addMoney")
    public String addMoney(@WebParam(name = "id") long id, @WebParam(name = "cash") double cash) {
        Customer customer = cm.getCustomer(id);
        if (customer != null) {
            Double balance = customer.getBalance();

            customer.setBalance(balance + cash);
            cm.modifyCustomer(customer);

            return Result.ACCEPT.getLabel() + ": old " + balance + ", new " + customer.getBalance();
        }

        return Result.CLIENT_NOT_FOUND.getLabel();
    }

    @WebMethod(operationName = "withdraw")
    public String withdraw(@WebParam(name = "id") long id, @WebParam(name = "cash") double cash) {
        Customer customer = cm.getCustomer(id);
        if (customer != null) {
            Double balance = customer.getBalance();

            if (balance >= cash) {
                customer.setBalance(balance - cash);
                cm.modifyCustomer(customer);

                return Result.ACCEPT.getLabel()  + ": old " + balance + ", new " + customer.getBalance();
            }

            return Result.DECLINE.getLabel();
        }
        return Result.CLIENT_NOT_FOUND.getLabel();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addProduct")
    public String addProduct(@WebParam(name = "customerID") Long customerID, @WebParam(name = "productName") String productName) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addCustomer")
    public String addCustomer(@WebParam(name = "name") String name, @WebParam(name = "lastName") String lastName) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStatuses")
    public List getStatuses() {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "removeProduct")
    public String removeProduct(@WebParam(name = "customerID") Long customerID, @WebParam(name = "productName") String productName) {
        //TODO write your implementation code here:
        return null;
    }

}
