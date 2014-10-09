package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import java.util.List;

public interface ProcessingLocal {

    List<OrderProcessing> getOrderSteps(Order order);

    List<String> completeOrder(String rawOrder);

    void tryNextStep(Order order);

    void cancelOrder(Order order);
}
