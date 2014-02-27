package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author darya
 */

@ManagedBean
@RequestScoped
public class NavigationBean {
    
    @EJB
    private CustomerManagerLocal cm;
    @EJB
    private OrderManagerLocal om;
        
    private Customer customer;
    private Orders order;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer currentCustomer) {
        this.customer = currentCustomer;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders currentOrder) {
        this.order = currentOrder;
    }
    
    public String toCustomerInfo(Customer customer){
        this.customer = customer;
        return "/faces/client_info.xhtml";
    }
    
    public String toOrderInfo(Orders order){
        this.order = order;
        return "/faces/order_info.xhtml";
    }
    
}
