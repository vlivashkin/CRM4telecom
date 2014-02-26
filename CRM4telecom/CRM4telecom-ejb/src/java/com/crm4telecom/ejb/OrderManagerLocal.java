package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Orders;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {
    Orders createOrder(OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId, Boolean technicalSupportFlag);

    void modifyOrder(Orders order);
    
    Orders modifyOrder(Long orderId, OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId);
    
    Orders setCustomer(Long orderId, Long customerId);
    
    Orders setCustomer(Orders order, Long customerId);
    
    Orders getOrder(Long orderId);

    List<Orders> getOrdersList();
    
    List<Orders> getOrdersList(String order);
}
