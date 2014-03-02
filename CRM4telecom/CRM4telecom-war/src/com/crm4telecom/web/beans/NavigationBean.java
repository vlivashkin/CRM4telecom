package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

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

    public String toCustomerInfo(Customer customer) {
        this.customer = customer;
        return "customer_info";
    }

    public String toOrderInfo(Orders order) {
        this.order = order;
        return "order_info";
    }

}
