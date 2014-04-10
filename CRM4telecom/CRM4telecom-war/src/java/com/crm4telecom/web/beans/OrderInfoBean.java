package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.omnifaces.cdi.ViewScoped;

@ManagedBean
@ViewScoped
public class OrderInfoBean implements Serializable {

    @EJB
    private OrderManagerLocal om;

    @Inject
    private OrderValidationBean ov;

    @Inject
    private OrderCommentBean oc;

    private Long id;
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        order = om.getOrder(id);
        ov.init(order);
        oc.init(order);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        ov.init(order);
        oc.init(order);
    }

    public OrderValidationBean getOv() {
        return ov;
    }

    public OrderCommentBean getComment() {
        return oc;
    }

    public String getClosed() {
        if (order != null && OrderStatus.CLOSED.equals(order.getStatus())) {
            return "disabled";
        } else {
            return "";
        }
    }

    public void create() {
        Order orderNew = new Order();
        ov.fillOrder(orderNew);
        om.createOrder(orderNew);
        order = orderNew;

        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info", "id", order.getOrderId().toString());
    }

    public void modify() {
        ov.fillOrder(order);
        om.modifyOrder(order);
        
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info", "id", order.getOrderId().toString());
    }

    public void toCustomer() {
        JSFHelper helper = new JSFHelper();
        helper.redirect("customer_info", "id", order.getCustomer().getCustomerId().toString());
    }
        
    public List<String> completeOrder(String order) {
        return om.completeOrder(order);
    }

    public List<OrderProcessing> getOrderSteps() {
        if (order != null) {
            return om.getOrderSteps(order);
        }
        return Collections.EMPTY_LIST;
    }

    public void nextStep() {
        om.toNextStep(order);
    }

    public void cancelOrder() {
        om.cancelOrder(order);
    }
}
