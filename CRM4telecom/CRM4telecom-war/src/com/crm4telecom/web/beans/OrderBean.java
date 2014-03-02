package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.web.beans.util.LazyOrderDataModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class OrderBean implements Serializable {

    private LazyDataModel<Orders> lazyModel;
    Orders order;
    List<String> priority;
    List<String> state;

    @EJB
    private OrderManagerLocal cm;

    public OrderBean() {
        state = new ArrayList<>();
        for (OrderState temp : OrderState.values()) {
            state.add(temp.name());
        }
        priority = new ArrayList<>();
        for (OrderPriority temp : OrderPriority.values()) {
            priority.add(temp.name());
        }
    }

    @PostConstruct
    public void init() {
        lazyModel = new LazyOrderDataModel(cm);
    }

    public LazyDataModel<Orders> getOrders() {
        return lazyModel;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public String onRowSelect() throws IOException {
        return "customer_info?includeViewParams=true";
    }

    public List<String> getPriority() {
        return priority;
    }

    public List<String> getState() {
        return state;
    }

}
