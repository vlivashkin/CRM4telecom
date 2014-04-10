package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.enums.UserType;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class ResourcesBean implements Serializable {

    @EJB
    private CustomerManagerLocal cm;

    @EJB
    private OrderManagerLocal om;

    @EJB
    private GetManagerLocal gm;

    public OrderPriority[] getPriorities() {
        return OrderPriority.values();
    }

    public OrderStatus[] getStatuses() {
        return OrderStatus.values();
    }

    public OrderType[] getTypes() {
        return OrderType.values();
    }

    public List<String> getProduct() {
        return gm.getProductList();
    }

    public UserType[] getUserTypes() {
        return UserType.values();
    }

    public Long getCustomersCount() {
        return cm.getCustomersCount();
    }

    public Long getOrdersCount() {
        return om.getOrdersCount();
    }
}
