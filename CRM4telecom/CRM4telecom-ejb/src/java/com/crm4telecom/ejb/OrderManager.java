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
    public Orders createOrder(Long customerId, Long productId, Date orderDate, String orderType, String typeComment, String status, String priority, Long managerId, String technicalSupportFlag) {
        Orders order = new Orders();
        fillOrder(order, orderDate, orderType, typeComment, status, priority, managerId, customerId, technicalSupportFlag, productId);
        em.persist(order);
        
        return order;
    }
    
    @Override
    public void modifyOrder(Orders order) {
        em.persist(order);
    }
    
    @Override
    public Orders modifyOrder(Long orderId, Long customerId, Long productId, Date orderDate, String orderType, String typeComment, String status, String priority, Long managerId, String technicalSupportFlag) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        fillOrder(order, orderDate, orderType, typeComment, status, priority, managerId, customerId, technicalSupportFlag, productId);
        em.persist(order);
        
        return order;
    }
    
    @Override
    public void setCustomer(Long orderId, Long customerId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        
        Customer customer = em.find(Customer.class, customerId); 
        if (customer == null)
            throw new NoSuchElementException();
        
        order.setCustomerId(customer.getCustomerId());
        em.persist(order);
    }
    
    @Override
    public Orders getOrder(Long orderId) {
        Orders order = em.find(Orders.class, orderId);
        return order;
    }

    @Override
    public List<Orders> getOrdersList() {
        return em.createNamedQuery("Orders.findAll").getResultList();
    }
    
    @Override
    public List<Orders> getOrdersList(String order) {
        return em.createQuery("SELECT o FROM Orders o ORDER BY " + order, Orders.class).getResultList();
    }
    
    private Orders fillOrder(Orders order, Date orderDate, String orderType, String typeComment, String status, String priority, Long customerId, Long managerId, String technicalSupportFlag, Long productId) {
        if (customerId != null) {
            Customer customer = em.find(Customer.class, customerId); 
            if (customer == null)
                throw new NoSuchElementException();
            order.setCustomerId(customer.getCustomerId());
        }
        
        if (productId != null) {
            Product product = em.find(Product.class, productId); 
            if (product == null)
                throw new NoSuchElementException();
            order.setProductId(product);
        }
        
        order.setOrderDate(orderDate);
        order.setOrderType(orderType);
        order.setTypeComment(typeComment);
        order.setStatus(status);
        order.setPriority(priority);
        order.setManagerId(managerId);
        order.setTechnicalSupportFlag(technicalSupportFlag);
        
        return order;
    }
}
