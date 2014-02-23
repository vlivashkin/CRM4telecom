package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.jpa.Product;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderManager implements OrderManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Orders getOrger(Long orderId) {
        Orders order = em.find(Orders.class, orderId);
        return order;
    }

    @Override
    public void alterOrder(Long orderId, Date orderDate, String orderType, String typeComment, String status, String priority, Customer customerId, Long managerId, String technicalSupportFlag, Product productId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        else {
            order.setOrderDate(orderDate);
            order.setOrderType(orderType);
            order.setTypeComment(typeComment);
            order.setStatus(status);
            order.setPriority(priority);
            order.setCustomerId(customerId);
            order.setManagerId(managerId);
            order.setTechnicalSupportFlag(technicalSupportFlag);
            order.setProductId(productId);
        }
    }

    @Override
    public void addOrder(Date orderDate, String orderType, String typeComment, String status, String priority, Customer customerId, Long managerId, String technicalSupportFlag, Product productId) {
        Orders order = new Orders();
        
        order.setOrderDate(orderDate);
        order.setOrderType(orderType);
        order.setTypeComment(typeComment);
        order.setStatus(status);
        order.setPriority(priority);
        order.setCustomerId(customerId);
        order.setManagerId(managerId);
        order.setTechnicalSupportFlag(technicalSupportFlag);
        order.setProductId(productId);
        
        em.persist(order);
    }

    @Override
    public List<Orders> getOrdersList() {
        return em.createQuery("select * from Orders", Orders.class).getResultList();
    }
    
    
    
}
