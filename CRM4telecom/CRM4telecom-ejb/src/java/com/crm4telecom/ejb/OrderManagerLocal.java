package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.jpa.Product;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {

    Orders getOrger(Long orderId);

    void alterOrder(Long orderId, Date orderDate, String orderType, String typeComment, String status, String priority, Customer customerId, Long managerId, String technicalSupportFlag, Product productId);

    void addOrder(Date orderDate, String orderType, String typeComment, String status, String priority, Customer customerId, Long managerId, String technicalSupportFlag, Product productId);

    List<Orders> getOrdersList();
   
}
