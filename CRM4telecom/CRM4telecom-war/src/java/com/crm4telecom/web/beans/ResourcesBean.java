package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.enums.OrderType;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

public class ResourcesBean implements Serializable {

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
}
