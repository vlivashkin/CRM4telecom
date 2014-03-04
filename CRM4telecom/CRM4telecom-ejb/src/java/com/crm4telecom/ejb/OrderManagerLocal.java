package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.ejb.util.OrderType;
import com.crm4telecom.jpa.Orders;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {

    Orders createOrder(OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId, Boolean technicalSupportFlag);

    void modifyOrder(Orders order);

    Orders modifyOrder(Long orderId, OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId);

    Orders setCustomer(Long orderId, Long customerId);

    Orders setCustomer(Orders order, Long customerId);

    Orders getOrder(Long orderId);

    List<Orders> getAllOrders();

    List<Orders> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters);

    Long getOrdersCount();

    Long getOrdersCount(Map<String, String> filters);
    
    Orders changeOrderState(Long orderId, OrderEvent event);

    void changeOrderState(Orders order, OrderEvent event);

    OrderState getOrderState(Long orderId);
    
    List<Orders> search(Map<String,List<String>> parametrs);
}
