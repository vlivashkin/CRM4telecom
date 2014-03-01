package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.jpa.Orders;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LifeCycleManager implements LifeCycleManagerLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Orders changeOrderState(Long orderId, OrderEvent event) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        changeOrderState(order, event);
        
        return order;
    }
    
    @Override
    public void changeOrderState(Orders order, OrderEvent event) {
        String rawState = order.getStatus();
        OrderState oldState = OrderState.valueOf(rawState);
        switch (event) {
            case CREATED:
                if (OrderState.NONE.equals(oldState)) {
                    order.setStatus(OrderState.NEW.name());
                }
                break;
            case SENT_TO_TECH_SUPPORT:
                if (OrderState.NEW.equals(oldState)) {
                    order.setStatus(OrderState.OPENED.name());
                }
                break;
            case ENGINEER_APPOINTED:
                if (OrderState.NEW.equals(oldState)) {
                    order.setStatus(OrderState.OPENED.name());
                }
                break;
            case DELAY:
                if (OrderState.OPENED.equals(oldState)) {
                    order.setStatus(OrderState.WAITING.name());
                }
                break;
            case READY:
                if (OrderState.WAITING.equals(oldState)) {
                    order.setStatus(OrderState.OPENED.name());
                }
                break;
            case CANCELLED:
                if (OrderState.WAITING.equals(oldState)) {
                    order.setStatus(OrderState.LOCKED.name());
                }
                break;
            case DONE:
                if (OrderState.OPENED.equals(oldState) || OrderState.LOCKED.equals(oldState)) {
                    order.setStatus(OrderState.CLOSED.name());
                }
                break;
            default:
                break;
        }
        em.merge(order);
    }
    
    @Override
    public OrderState getOrderState(Long orderId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null)
            throw new NoSuchElementException();
        String rawState = order.getOrderType();

        return OrderState.valueOf(rawState);
    }
}
