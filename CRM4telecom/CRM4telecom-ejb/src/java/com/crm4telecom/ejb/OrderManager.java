package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.SearchQuery;
import com.crm4telecom.ejb.filling.IpFillingLocal;
import com.crm4telecom.ejb.filling.PhoneFillingLocal;
import com.crm4telecom.orchestrator.OrderStatus;
import com.crm4telecom.orchestrator.OrderStep;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.enums.ProductProperties;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.mail.MailManager;
import com.crm4telecom.orchestrator.Task;
import com.crm4telecom.orchestrator.TaskType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

@Stateless
public class OrderManager implements OrderManagerLocal {

    private final Logger log = Logger.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;

    @EJB
    private IpFillingLocal ipFilling;

    @EJB
    private PhoneFillingLocal phoneFilling;

    @Override
    public Order createOrder(Order order) {
        Date date = new Date();

        order.setOrderDate(date);
        order.setStatus(OrderStatus.NEW);
        order.setProcessStep(OrderStep.PRE_CONFIRM);
        em.persist(order);

        OrderProcessing op = new OrderProcessing();
        op.setOrderId(order.getOrderId());
        op.setStartDate(date);
        op.setStepName(OrderStep.PRE_CONFIRM);
        em.persist(op);

        return order;
    }

    @Override
    public void modifyOrder(Order order) {
        em.merge(order);
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

    @Override
    public List<String> completeOrder(String rawOrder) {
        List<String> orders = new ArrayList<String>();
        String raw = rawOrder.trim();
        Long id = 1L;
        if (raw.matches("^\\d+$")) {
            id = Long.parseLong(raw);
            Order ord = em.find(Order.class, id);
            if (ord != null) {
                orders.add(ord.toString());
            }
        } else {
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("Can't find order by  " + Long.toString(id) + " in Orders table");
            }
        }

        return orders;
    }

    @Override
    public List<OrderProcessing> getOrderSteps(Order order) {
        String sqlQuery = "SELECT o FROM OrderProcessing o WHERE o.orderId = :id ORDER BY o.startDate";
        Query query = em.createQuery(sqlQuery).setParameter("id", order.getOrderId());
        return query.getResultList();
    }

    @Override
    public void toNextStep(Order order) {

        if (order.getStatus() != OrderStatus.CLOSED
                && order.getStatus() != OrderStatus.CANCELLED) {
            OrderStep nextStep = order.getProcessStep();
            Task task = order.getProcessStep().getTask();
            if (task.getType().equals(TaskType.AUTO_TASK)) {
                if (task.run()) {
                    nextStep = order.getProcessStep().nextStep(order.getTechSupport());

                } else {
                    nextStep.getTask().doneStatus = OrderStatus.ERROR;
                }

            } else {
                nextStep = order.getProcessStep().nextStep(order.getTechSupport());
            }

            // update end date for previous step in OrderProcessing
            if (nextStep.getStatus() != OrderStatus.ERROR) {
                String sqlQuery = "SELECT o FROM OrderProcessing o WHERE o.orderId = :id AND o.endDate IS NULL ORDER BY o.startDate DESC";
                Query query = em.createQuery(sqlQuery).setParameter("id", order.getOrderId());
                OrderProcessing oldStep = (OrderProcessing) query.getSingleResult();
                oldStep.setEndDate(new Date());
                em.merge(oldStep);
            }
            // update status
            order.setStatus(order.getProcessStep().getStatus());
            em.merge(order);

            if (nextStep != order.getProcessStep()) {
                OrderProcessing newStep = new OrderProcessing();

                // create new step in OrderProcessing
                newStep.setOrderId(order.getOrderId());
                newStep.setStartDate(new Date());
                newStep.setStepName(nextStep);
                em.persist(newStep);

                // update information about current step in order
                order.setProcessStep(nextStep);
                em.merge(order);

                // use new ip/phone
                //to run
                if (nextStep == OrderStep.POST_CONFIRM) {
                    ProductProperties properties = order.getProduct().getProperties();
                    if (properties.equals(ProductProperties.IP)) {
                        if (order.getOrderType().equals(OrderType.CONNECT)) {
                            ipFilling.allocateItem(order.getCustomer());
                        } else {
                            ipFilling.freeItem(order.getCustomer());
                        }
                    } else if (properties.equals(ProductProperties.PHONE)) {
                        if (order.getOrderType().equals(OrderType.CONNECT)) {
                            phoneFilling.allocateItem(order.getCustomer());
                        } else {
                            phoneFilling.freeItem(order.getCustomer());
                        }
                    }
                }

                // send email 'status changed'
                try {
                    MailManager mm = new MailManager();
                    mm.statusChangedEmail(order, getOrderSteps(order));
                } catch (MessagingException e) {
                    if (log.isEnabledFor(Priority.ERROR)) {
                        log.warn("Cant send email for orderId " + order.getOrderId() + " at order step " + getOrderSteps(order) + " at address " + order.getCustomer().getEmail(), e);
                    }
                }

            }
            if(order.getProcessStep().getTask().getType().equals(TaskType.AUTO_TASK)&&order.getStatus() != OrderStatus.CLOSED
                && order.getStatus() != OrderStatus.CANCELLED && order.getStatus() != OrderStatus.ERROR){
                toNextStep(order);
            }
        }
    }

    @Override
    public void cancelOrder(Order order) {
        if (order.getStatus() != OrderStatus.CLOSED
                && order.getStatus() != OrderStatus.CANCELLED) {
            order.setStatus(OrderStatus.CANCELLED);
            em.merge(order);
        }
    }
}
