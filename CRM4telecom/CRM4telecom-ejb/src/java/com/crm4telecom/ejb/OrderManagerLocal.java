package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Order;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {

    Order createOrder(Order order);

    void modifyOrder(Order order);

    Order getOrder(Long orderId);

    List<Order> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters, Map<String, List<String>> parametrs);

    Long getOrdersCount();
            
    Long getOrdersCount(Map<String, Object> filters, Map<String, List<String>> parametrs);

   }
