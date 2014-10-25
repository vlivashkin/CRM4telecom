package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.SearchQuery;
import com.crm4telecom.orchestrator.OrderStatus;
import com.crm4telecom.orchestrator.OrderStep;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

@Stateless
public class OrderManager implements OrderManagerLocal, OrderManagerRemote {

    private final Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order createOrder(Order order) {
        Date date = new Date();

        order.setOrderDate(date);
        order.setStatus(OrderStatus.NEW);
        order.setProcessStep(OrderStep.PRE_CONFIRM);
        em.persist(order);
        em.flush();

        OrderProcessing op = new OrderProcessing();
        op.setOrderId(order.getOrderId());
        op.setStartDate(date);
        op.setStepName(OrderStep.PRE_CONFIRM);
        em.persist(op);
        em.flush();

        return order;
    }

    @Override
    public void modifyOrder(Order order) {
        em.merge(order);
        em.flush();
    }

    @Override
    public Order getOrder(Long orderId) {

        Order order = em.find(Order.class, orderId);
        return order;
    }

    @Override
    public List<Order> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = SearchQuery.getSearchQuery("c FROM Orders c", parametrs, sortField, sortOrder);

        if (log.isInfoEnabled()) {
            log.info("Make query in Order table " + sqlQuery);
        }

        Query query = em.createQuery(sqlQuery, Order.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long getOrdersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c";
        Query query = em.createQuery(sqlQuery, Order.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getOrdersCount(Map<String, Object> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = SearchQuery.getSearchQuery("COUNT(c) FROM Orders c", parametrs);

        if (log.isInfoEnabled()) {
            log.info("Make query in Order table " + sqlQuery);
        }

        Query query = em.createQuery(sqlQuery, Order.class);
        return (Long) query.getSingleResult();
    }

}
