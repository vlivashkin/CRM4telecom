package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {

    Order createOrder(Order order);

    void modifyOrder(Order order);

    Order getOrder(Long orderId);

    List<Order> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters, Map<String, List<String>> parametrs);

    Long getOrdersCount(Map<String, String> filters, Map<String, List<String>> parametrs);

    public List<OrderProcessing> getOrderSteps(Order order);

    List<String> completeOrder(String rawOrder);

    void toNextStep(Order order);

    void cancelOrder(Order order);
}
