package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.util.LazyOrderDataModel;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;

@Named
@ViewScoped
public class OrderListBean implements Serializable, IListBean<Order> {

    private static final long serialVersionUID = 1L;

    @EJB
    private OrderManagerLocal om;

    private LazyOrderDataModel lazyModel;
    private List<Order> orders;
    private Order selected;

    private String fromID;
    private String toID;

    String order;
    String customer;
    String manager;
    String employee;
    List<String> selectedPriorities;
    List<String> selectedStatuses;
    Date fromDate;
    Date toDate;

    @Override
    @PostConstruct
    public void init() {
        lazyModel = new LazyOrderDataModel(om);
    }

    @Override
    public LazyDataModel<Order> getLazyModel() {
        lazyModel.setSearch(this);
        return lazyModel;
    }

    @Override
    public Order getSelected() {
        return selected;
    }

    @Override
    public void setSelected(Order selected) {
        this.selected = selected;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
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

    public List<String> getSelectedPriorities() {
        return selectedPriorities;
    }

    public void setSelectedPriorities(List<String> selectedPriorities) {
        this.selectedPriorities = selectedPriorities;
    }

    public List<String> getSelectedStatuses() {
        return selectedStatuses;
    }

    public void setSelectedStatuses(List<String> selectedStatuses) {
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

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getCustomers() {
        return orders;
    }

    @Override
    public void onRowSelect() {
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info", "id", selected.getOrderId().toString());
    }
}
