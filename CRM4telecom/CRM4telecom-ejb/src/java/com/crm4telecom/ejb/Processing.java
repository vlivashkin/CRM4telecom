package com.crm4telecom.ejb;

import com.crm4telecom.ejb.filling.IpFillingRemote;
import com.crm4telecom.ejb.filling.PhoneFillingRemote;
import com.crm4telecom.ejb.filling.ProductFilling;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.enums.ProductProperties;
import com.crm4telecom.enums.RemoteBean;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.mail.MailManager;
import com.crm4telecom.orchestrator.OrderStatus;
import com.crm4telecom.orchestrator.OrderStep;
import com.crm4telecom.orchestrator.Task;
import com.crm4telecom.orchestrator.TaskType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

@Stateless
public class Processing implements ProcessingLocal {
    
    @EJB
    private ProductFilling productFilling;
    
    @PersistenceContext
    private EntityManager em;
    
    private final Logger log = Logger.getLogger(getClass().getName());
    
    @Override
    public List<String> completeOrder(String rawOrder) {
        List<String> orders = new ArrayList<>();
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
    public void tryNextStep(Order order) {
        
        if (order.getStatus() != OrderStatus.CLOSED && order.getStatus() != OrderStatus.CANCELLED) {
            OrderStep currentStep = order.getProcessStep();
            Task task = currentStep.getTask();

            //sending parametes to task
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("Type", Boolean.toString(order.getOrderType().equals(OrderType.CONNECT)));
            parameters.put("Product", order.getProduct().getProductId().toString());
            parameters.put("Customer", order.getCustomerId().toString());
            parameters.put("Order", order.getOrderId().toString());
            task.setParameters(parameters);
            
            if (task.getType().equals(TaskType.AUTO_TASK) && task.run() || task.getType().equals(TaskType.USER_TASK)) {
                doneCurrentStep(order);
                
                if (order.getStatus() != OrderStatus.CLOSED) {
                    toNextStep(order);
                    if (order.getProcessStep().getTask().getType().equals(TaskType.AUTO_TASK)) {
                        tryNextStep(order);
                    }
                } else {
                    productFilling.addProduct(order);
                    mailNotification(order);
                }
            } else {
                order.setStatus(OrderStatus.ERROR);
                em.merge(order);
                em.flush();
            }
        }
    }
    
    private void doneCurrentStep(Order order) {
        String sqlQuery = "SELECT o FROM OrderProcessing o WHERE o.orderId = :id AND o.endDate IS NULL ORDER BY o.startDate DESC";
        Query query = em.createQuery(sqlQuery).setParameter("id", order.getOrderId());
        OrderProcessing oldStep = (OrderProcessing) query.getSingleResult();
        oldStep.setEndDate(new Date());
        em.merge(oldStep);
        
        OrderStep currentStep = order.getProcessStep();
        order.setStatus(currentStep.getStatus());
        em.merge(order);
    }
    
    private void toNextStep(Order order) {
        OrderStep currentStep = order.getProcessStep();
        OrderStep nextStep = currentStep.nextStep(order.getTechSupport());

        // create new step in OrderProcessing
        OrderProcessing newStep = new OrderProcessing();
        newStep.setOrderId(order.getOrderId());
        newStep.setStartDate(new Date());
        newStep.setStepName(nextStep);
        em.persist(newStep);

        // update information about current step in order
        order.setProcessStep(nextStep);
        em.merge(order);
    }
    
    private void mailNotification(Order order) {
        try {
            MailManager mm = new MailManager();
            mm.statusChangedEmail(order, getOrderSteps(order));
        } catch (MessagingException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.warn("Cant send email for orderId " + order.getOrderId() + " at order step " + getOrderSteps(order) + " at address " + order.getCustomer().getEmail(), e);
            }
        }
    }
    
    @Override
    public void cancelOrder(Order order) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
        Context ctx;
        try {
            ctx = new InitialContext();
            ProductProperties properties = order.getProduct().getProperties();
            if (properties.equals(ProductProperties.IP)) {
                IpFillingRemote ipFillingRemote = (IpFillingRemote) ctx.lookup(RemoteBean.IpFilling.getJndi());
                
                ipFillingRemote.freeItem(order.getCustomer());
                
            } else if (properties.equals(ProductProperties.PHONE)) {
                PhoneFillingRemote phoneFillingRemote = (PhoneFillingRemote) ctx.lookup(RemoteBean.PhoneFilling.getJndi());
                
                phoneFillingRemote.freeItem(order.getCustomer());
                
            }
        } catch (Throwable e) {
            logger.warning(e.toString());
        }
        if (order.getStatus() != OrderStatus.CLOSED
                && order.getStatus() != OrderStatus.CANCELLED) {
            order.setStatus(OrderStatus.CANCELLED);
            em.merge(order);
        }
    }
}
