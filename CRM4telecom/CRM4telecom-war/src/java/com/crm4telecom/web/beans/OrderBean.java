package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.enums.OrderEvent;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.util.LazyOrderDataModel;
import com.crm4telecom.web.util.JSFHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class OrderBean implements Serializable {

    private LazyOrderDataModel lazyModel;
    Order order;
    OrderEvent event;

    @EJB
    private OrderManagerLocal om;

    @EJB
    private GetManagerLocal gm;

    @Inject
    private OrderValidationBean ov;

    @Inject
    private OrderSearchBean search;

    @Inject
    private OrderCommentBean comment;

    @PostConstruct
    public void init() {
        lazyModel = new LazyOrderDataModel(om);
    }

    public OrderValidationBean getValidation() {
        return ov;
    }

    public OrderSearchBean getSearch() {
        return search;
    }

    public OrderCommentBean getComment() {
        return comment;
    }

    public LazyDataModel<Order> getOrders() {
        lazyModel.setSearch(search);
        return lazyModel;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        ov.init(order);
        comment.init(order);
    }

    public void onRowSelect() throws IOException {
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info");
    }

    public OrderPriority[] getPriorities() {
        return OrderPriority.values();
    }

    public OrderStatus[] getStatuses() {
        return OrderStatus.values();
    }

    public List<String> getProduct() {
        return gm.getProductList();
    }

    public void create() {
        Order orderNew = new Order();
        ov.fillOrder(orderNew);
        om.createOrder(orderNew);

        JSFHelper helper = new JSFHelper();
        helper.redirect("order_list");
    }

    public void modify() {
        ov.fillOrder(order);
        om.modifyOrder(order);
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info");
    }

    public List<OrderEvent> getEvents() {
        List<OrderEvent> events = new ArrayList<>();
        if (order != null) {
            OrderStatus status = order.getStatus();
            if (status != null) {
                events = status.possibleEvents();
            }
        }
        return events;
    }

    public OrderEvent getEvent() {
        return event;
    }

    public void setEvent(OrderEvent event) {
        this.event = event;
    }

    public void changeState() {
        System.out.println(order + " " + event);
        om.changeOrderState(order, event);
    }

    public List<String> completeOrder(String order) {
        return om.completeOrder(order);
    }

    public void toAddOrder() {
        ov.init();

        JSFHelper helper = new JSFHelper();
        helper.redirect("order_add");
    }
}
