package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.web.beans.util.LazyOrderDataModel;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class OrderListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private LazyDataModel<Orders> lazyModel;
    private Orders selectedCustomer;

    @EJB
    private OrderManagerLocal cm;

    @PostConstruct
    public void init() {
        lazyModel = new LazyOrderDataModel(cm);
    }

    public LazyDataModel<Orders> getOrders() {
        return lazyModel;
    }

    public Orders getSelectedOrder() {
        return selectedCustomer;
    }

    public void setSelectedOrder(Orders selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    
    public String onRowSelect() throws IOException {
        return "customer_info?includeViewParams=true";
    }
}
