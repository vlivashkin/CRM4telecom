package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.ejb.util.OrderType;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.jpa.Product;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OrderManager implements OrderManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Orders createOrder(OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId, Boolean technicalSupportFlag) {
        Orders order = new Orders();
        fillOrder(order, type, typeComment, productId, priority, managerId);
        order.setOrderDate(new Date());
        order.changeOrderState(OrderEvent.CREATED);
        order.setTechnicalSupportFlag(technicalSupportFlag.toString());
        em.persist(order);
        changeOrderState(order, OrderEvent.CREATED);

        return order;
    }

    @Override
    public void modifyOrder(Orders order) {
        Long orderId = order.getOrderId();
        Orders oldOrder = em.find(Orders.class, orderId);
        if (order.getStatus().equals(oldOrder.getStatus())) {
            em.merge(order);
        }
    }

    @Override
    public Orders modifyOrder(Long orderId, OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null) {
            throw new NoSuchElementException();
        }
        fillOrder(order, type, typeComment, productId, priority, managerId);
        em.merge(order);

        return order;
    }

    @Override
    public Orders setCustomer(Long orderId, Long customerId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null) {
            throw new NoSuchElementException();
        }

        return setCustomer(order, customerId);
    }

    @Override
    public Orders setCustomer(Orders order, Long customerId) {
        if (customerId != null) {
            Customer customer = em.find(Customer.class, customerId);
            if (customer == null) {
                throw new NoSuchElementException();
            }
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
    public List<Orders> getAllOrders() {
        return em.createQuery("SELECT c FROM Orders c").getResultList();
    }

    @Override
    public List<Orders> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters) {
        String sqlQuery = "SELECT c FROM Orders c";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " c." + filterProperty + " like \'" + filterValue + "%\' AND";
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        if (sortField != null && !"".equals(sortField)) {
            sqlQuery += " ORDER BY c." + sortField;
        }
        if ("DESCENDING".endsWith(sortOrder)) {
            sqlQuery += " DESC";
        }

        Query query = em.createQuery(sqlQuery, Orders.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long getOrdersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c";

        Query query = em.createQuery(sqlQuery, Orders.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getOrdersCount(Map<String, String> filters) {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " c." + filterProperty + " like \'" + filterValue + "%\' AND";
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        Query query = em.createQuery(sqlQuery, Orders.class);
        return (Long) query.getSingleResult();
    }

    private Orders fillOrder(Orders order, OrderType type, String typeComment, Long productId, OrderPriority priority, Long managerId) {
        if (productId != null) {
            Product product = em.find(Product.class, productId);
            if (product == null) {
                throw new NoSuchElementException();
            }
            order.setProductId(product);
        }

        order.setOrderType(type.name());
        order.setTypeComment(typeComment);
        order.setPriority(priority.name());
        order.setManagerId(managerId);

        return order;
    }

    @Override
    public OrderState getOrderState(Long orderId) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null) {
            throw new NoSuchElementException();
        }
        String rawState = order.getOrderType();

        return OrderState.valueOf(rawState);
    }

    @Override
    public Orders changeOrderState(Long orderId, OrderEvent event) {
        Orders order = em.find(Orders.class, orderId);
        if (order == null) {
            throw new NoSuchElementException();
        }
        changeOrderState(order, event);

        return order;
    }

    @Override
    public void changeOrderState(Orders order, OrderEvent event) {
        order.changeOrderState(event);
        em.merge(order);
    }

}
