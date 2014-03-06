package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.web.beans.util.LazyOrderDataModel;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class OrderBean implements Serializable {

    private LazyDataModel<Orders> lazyModel;
    Orders order;


    @EJB
    private OrderManagerLocal om;

    @Inject
    private OrderValidationBean ov;

    @PostConstruct
    public void init() {
        lazyModel = new LazyOrderDataModel(om);
    }

    public LazyDataModel<Orders> getOrders() {
        return lazyModel;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
        ov.init(order);
    }

    public void onRowSelect() throws IOException {
        ConfigurableNavigationHandler configurableNavigationHandler
                = (ConfigurableNavigationHandler) FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation("order_info?includeViewParams=true");
    }

    public OrderPriority[] getPriorities() {
        return OrderPriority.values();
    }

    public OrderState[] getStates() {
        return OrderState.values();
    }
    
    public void create() {
        ov.fillOrder(order);
        om.createOrder(order);
    }

    public void modify() {
        ov.fillOrder(order);
        om.modifyOrder(order);
    }

    public OrderValidationBean getOv() {
        return ov;
    }

    public void setOv(OrderValidationBean ov) {
        this.ov = ov;
    }
    
}
