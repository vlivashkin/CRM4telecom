package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.jpa.Orders;
import javax.ejb.Local;

@Local
public interface LifeCycleManagerLocal {

    Orders changeOrderState(Long orderId, OrderEvent event);

    void changeOrderState(Orders order, OrderEvent event);

    OrderState getOrderState(Long orderId);
}
