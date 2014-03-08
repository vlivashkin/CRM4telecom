package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.jpa.Orders;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@ManagedBean
@SessionScoped
public class OrderSearchBean implements Serializable {

    @EJB
    private OrderManagerLocal om;

    String order;
    String customer;
    String manager;
    String employee;
    List<OrderPriority> selectedPriorities;
    List<OrderState> selectedStatuses;
    Date fromDate;
    Date toDate;

    public OrderManagerLocal getOm() {
        return om;
    }

    @PostConstruct
    public void init() {

    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public List<OrderPriority> getSelectedPriorities() {
        return selectedPriorities;
    }

    public void setSelectedPriorities(List<OrderPriority> selectedPriorities) {
        this.selectedPriorities = selectedPriorities;
    }

    public List<OrderState> getSelectedStatuses() {
        return selectedStatuses;
    }

    public void setSelectedStatuses(List<OrderState> selectedStatuses) {
        this.selectedStatuses = selectedStatuses;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
