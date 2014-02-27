package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.OrderPriority;
import com.crm4telecom.ejb.OrderState;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author darya
 */

@ManagedBean
@RequestScoped
public class RequestBean {
        
    @EJB
    private CustomerManagerLocal cm;
    @EJB
    private OrderManagerLocal om;
    
    public List<Customer> getCustomersList(){
        List<Customer> customers =  cm.getCustomerList();
        
        return customers;
    }
    
    public List<Orders> getOrdersList(){
        List<Orders> orders =  om.getOrdersList();
        
        return orders;
    }
    
    public List<String> getStatuses(){
        List<String> state = new ArrayList<>();
        OrderState[] orderStates = OrderState.values();
        for (int i = 0; i < orderStates.length; i++) {
            state.add(orderStates[i].toString());
        }
        return state;
    }
    
    public List<String> getPriority(){
        List<String> priority = new ArrayList<>();
        OrderPriority[] orderPriority = OrderPriority.values();
        for (int i = 0; i < orderPriority.length; i++) {
            priority.add(orderPriority[i].toString());
        }
        return priority;
    }
    
}
