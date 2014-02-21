package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.jpa.Product;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface OrderManagerLocal {

    Orders getOrger(BigDecimal orderId);

    void alterOrder(BigDecimal orderId, Date orderDate, String orderType, String typeComment, String status, String priority, Customer customerId, BigInteger managerId, String technicalSupportFlag, Product productId);

    void addOrder(Date orderDate, String orderType, String typeComment, String status, String priority, Customer customerId, BigInteger managerId, String technicalSupportFlag, Product productId);

    List<Orders> getOrdersList();
   
}
