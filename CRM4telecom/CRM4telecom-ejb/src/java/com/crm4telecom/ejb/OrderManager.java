package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderType;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.jpa.Product;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderManager implements OrderManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private LifeCycleManagerLocal lcm;
    
    @Override
    public Orders createOrder(OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId, Boolean technicalSupportFlag) {
        Orders order = new Orders();
        fillOrder(order, type, typeComment, productId, priority, managerId);
        order.setOrderDate(new Date());
        order.setStatus(OrderState.NONE.name());
        order.setTechnicalSupportFlag(technicalSupportFlag.toString());
        em.persist(order);
        lcm.changeOrderState(order, OrderEvent.CREATED);
        if (technicalSupportFlag) {
            lcm.changeOrderState(order, OrderEvent.SENT_TO_TECH_SUPPORT);
        } else {
            lcm.changeOrderState(order, OrderEvent.ENGINEER_APPOINTED);
        }
        
        return order;
    }
    
    @Override
    public void modifyOrder(Orders order) {
        Long orderId = order.getOrderId();
        Orders oldOrder = em.find(Orders.class, orderId);
        if (order.getStatus().equals(oldOrder.getStatus()))
            em.merge(order);
    }
    
    @Override
    public Orders modifyOrder(Long orderId, OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        fillOrder(order, type, typeComment, productId, priority, managerId);
        em.merge(order);
        
        return order;
    }
    
    @Override
    public Orders setCustomer(Long orderId, Long customerId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        
        return setCustomer(order, customerId);
    }
    
    @Override
    public Orders setCustomer(Orders order, Long customerId) {
        if (customerId != null) {
            Customer customer = em.find(Customer.class, customerId); 
            if (customer == null)
                throw new NoSuchElementException();
            order.setCustomerId(customer);
            em.merge(order);
        }
        
        return order;
    }
    
    @Override
    public Orders getOrder(Long orderId) {
        Orders order = em.find(Orders.class, orderId);
        return order;
    }

    @Override
    public List<Orders> getOrdersList() {
        return em.createQuery("SELECT o FROM Orders o", Orders.class).getResultList();
    }
    
    @Override
    public List<Orders> getOrdersList(String order) {
        return em.createQuery("SELECT o FROM Orders o ORDER BY :order", Orders.class).setParameter("order", order).getResultList();
    }
    
    private Orders fillOrder(Orders order, OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId) {
        if (productId != null) {
            Product product = em.find(Product.class, productId); 
            if (product == null)
                throw new NoSuchElementException();
            order.setProductId(product);
        }
        
        order.setOrderType(type.name());
        order.setTypeComment(typeComment);
        order.setPriority(priority.name());
        order.setManagerId(managerId);
        
        return order;
    }
}
