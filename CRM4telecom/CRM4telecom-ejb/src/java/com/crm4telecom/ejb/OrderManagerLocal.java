package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Orders;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {
    public Orders createOrder(Long customerId, Long productId, Date orderDate, String orderType, String typeComment, String status, String priority, Long managerId, String technicalSupportFlag);

    public void modifyOrder(Orders order);
    
    public Orders modifyOrder(Long orderId, Long customerId, Long productId, Date orderDate, String orderType, String typeComment, String status, String priority, Long managerId, String technicalSupportFlag);
    
    void setCustomer(Long orderId, Long customerId);
    
    Orders getOrder(Long orderId);

    List<Orders> getOrdersList();
    
    List<Orders> getOrdersList(String order);
}
