package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.enums.CustomerStatus;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.enums.UserType;
import com.crm4telecom.jpa.Product;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ResourcesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerManagerLocal cm;

    @EJB
    private OrderManagerLocal om;

    @EJB
    private GetManagerLocal gm;

    public OrderPriority[] getPriorities() {
        return OrderPriority.values();
    }

    public OrderStatus[] getOrderStatuses() {
        return OrderStatus.values();
    }

    public CustomerStatus[] getCustomerStatuses() {
        return CustomerStatus.values();
    }

    public OrderType[] getTypes() {
        return OrderType.values();
    }

    public List<Product> getProducts() {
        return gm.getProductList();
    }

    public List<String> getMarkets() {
        return gm.getMarketList();
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

    public Date getCurrentTime() {
        return new Date();
    }
}
