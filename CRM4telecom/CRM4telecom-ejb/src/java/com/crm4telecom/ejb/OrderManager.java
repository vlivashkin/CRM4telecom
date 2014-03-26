package com.crm4telecom.ejb;

import com.crm4telecom.ejb.filling.IpFillingLocal;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.enums.OrderStep;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.mail.MailManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OrderManager implements OrderManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private IpFillingLocal ipFilling;

    @Override
    public Order createOrder(Order order) {
        Date date = new Date();
        order.setOrderDate(date);
        order.setStatus(OrderStatus.NEW);
        if (order.getTechnicalSupportFlag()) {
            order.setProcessStep(OrderStep.SEND_TO_TECH_SUPPORT);
        } else {
            order.setProcessStep(OrderStep.ENGINEER_APPOINT);
        }
        em.persist(order);
        OrderProcessing op = new OrderProcessing();
        op.setOrderId(order.getOrderId());
        op.setStartDate(date);
        if (order.getTechnicalSupportFlag()) {
            op.setStepEvent(OrderStep.SEND_TO_TECH_SUPPORT);
        } else {
            op.setStepEvent(OrderStep.ENGINEER_APPOINT);

        }
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
    public List<Order> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT c FROM Orders c";
        if (!parametrs.isEmpty()) {
            sqlQuery += " WHERE";
            for (String paramProperty : parametrs.keySet()) {
                List<String> val = (List<String>) parametrs.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (String val1 : val) {
                        sqlQuery += " LOWER(c." + paramProperty + ") REGEXP LOWER('" + val1 + "') OR";
                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    String check = (String) paramProperty;
                    if (check.compareTo("fromDate") != 0 && check.compareTo("toDate") != 0) {
                        if (check.compareTo("customerId") == 0) {
                            Pattern p = Pattern.compile("[0-9]{1,}");
                            Pattern p1 = Pattern.compile("#[0-9]{1,}");
                            Pattern p2 = Pattern.compile("[a-zA-Z]{1,}");
                            Matcher m2 = p2.matcher(val.get(0));
                            Matcher m1 = p1.matcher(val.get(0));
                            Matcher m = p.matcher(val.get(0));

                            if (m2.matches()) {
                                sqlQuery += " LOWER( c.customerId.firstName ) REGEXP LOWER ('" + val.get(0) + "') OR LOWER( c.customerId.lastName ) REGEXP LOWER ('" + val.get(0) + "')  AND";
                            }

                            if (m.matches()) {
                                sqlQuery += "  LOWER ( c.customerId.customerId )  REGEXP LOWER ('" + val.get(0) + "') AND";
                            }
                            if (m1.matches()) {
                                sqlQuery += "  LOWER ( c.customerId.customerId )  REGEXP LOWER ('" + val.get(0).substring(1) + "') AND";
                            }
                        } else {
                            sqlQuery += "   LOWER( c." + paramProperty + " ) REGEXP LOWER('" + val.get(0) + "')   AND";
                        }
                    } else {
                        if (check.compareTo("fromDate") == 0) {
                            sqlQuery += " c.orderDate > CAST(CAST( '" + val.get(0) + "' AS DATE ) AS TIMESTAMP)     AND";
                        }
                        if (check.compareTo("toDate") == 0) {
                            sqlQuery += " c.orderDate < CAST( CAST( '" + val.get(0) + "' AS DATE) AS TIMESTAMP) +1   AND";
                        }
                    }
                }
            }
        }

        if (filters != null && !filters.isEmpty()) {
            if (parametrs.isEmpty()) {
                sqlQuery += " WHERE";
            }
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " LOWER(c." + filterProperty + ") like LOWER ( \'%" + filterValue + "%\' ) AND";
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        }
        if (sortField != null && !"".equals(sortField)) {
            sqlQuery += " ORDER BY c." + sortField;
        }
        if ("DESCENDING".endsWith(sortOrder)) {
            sqlQuery += " DESC";
        }
        if (sqlQuery.endsWith("WHERE")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "WHERE".length());
        }
        if (sqlQuery.endsWith("AND")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "AND".length());
        }
        Query query = em.createQuery(sqlQuery, Order.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        System.out.println(sqlQuery);
        return query.getResultList();
    }

    @Override
    public Long getOrdersCount(Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c       ";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " LOWER( c." + filterProperty + ") like LOWER ( \'%" + filterValue + "%\' )  AND";
            }

        }
        if (!parametrs.isEmpty()) {
            if (filters.isEmpty()) {
                sqlQuery += " WHERE";
            }
            for (String paramProperty : parametrs.keySet()) {
                List<String> val = (List<String>) parametrs.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (String val1 : val) {
                        sqlQuery += "   LOWER( c." + paramProperty + "  ) REGEXP LOWER('" + val1 + "') OR";
                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    String check = (String) paramProperty;
                    if (check.compareTo("fromDate") != 0 && check.compareTo("toDate") != 0) {
                        if (check.compareTo("customerId") == 0) {
                            Pattern p = Pattern.compile("[0-9]{1,}");
                            Pattern p1 = Pattern.compile("#[0-9]{1,}");
                            Matcher m1 = p1.matcher(val.get(0));
                            Matcher m = p.matcher(val.get(0));
                            Pattern p2 = Pattern.compile("[a-zA-Z]{1,}");
                            Matcher m2 = p2.matcher(val.get(0));

                            if (m2.matches()) {
                                sqlQuery += " LOWER( c.customerId.firstName ) REGEXP LOWER ('" + val.get(0) + "') OR LOWER( c.customerId.lastName ) REGEXP LOWER ('" + val.get(0) + "')  AND";
                            }
                            if (m.matches()) {
                                sqlQuery += "  LOWER ( c.customerId.customerId )  REGEXP LOWER ('" + val.get(0) + "') AND";
                            }
                            if (m1.matches()) {
                                sqlQuery += "  LOWER ( c.customerId.customerId )  REGEXP LOWER ('" + val.get(0).substring(1) + "') AND";
                            }
                        } else {
                            sqlQuery += " LOWER( c." + paramProperty + " ) REGEXP LOWER('" + val.get(0) + "')   AND";
                        }
                    } else {
                        if (check.compareTo("fromDate") == 0) {
                            sqlQuery += " c.orderDate > CAST(CAST( '" + val.get(0) + "' AS DATE ) AS TIMESTAMP)  AND";
                        }
                        if (check.compareTo("toDate") == 0) {
                            sqlQuery += " c.orderDate < CAST( CAST( '" + val.get(0) + "' AS DATE) AS TIMESTAMP) +1 AND";
                        }
                    }
                }
            }
        }
        if (sqlQuery.endsWith("WHERE")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "WHERE".length());
        }
        if (sqlQuery.endsWith("AND")) {
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "AND".length());
        }
        System.out.println(sqlQuery);
        Query query = em.createQuery(sqlQuery, Order.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<String> completeOrder(String rawOrder) {
        List<String> orders = new ArrayList<>();
        String raw = rawOrder.trim();

        if (raw.matches("^\\d+$")) {
            Long id = Long.parseLong(raw);
            Order ord = em.find(Order.class, id);
            if (ord != null) {
                orders.add(ord.toString());
            }
        } else {
            System.out.println("stroka " + raw);
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
        OrderStep nextStep = order.getProcessStep().nextStep();

        if (nextStep != order.getProcessStep()) {
            OrderProcessing newStep = new OrderProcessing();

            // update end date for previous step in OrderProcessing
            String sqlQuery = "SELECT o FROM OrderProcessing o WHERE o.orderId = :id AND o.endDate = null ORDER BY o.startDate DESC";
            Query query = em.createQuery(sqlQuery).setParameter("id", order.getOrderId());
            OrderProcessing oldStep = (OrderProcessing) query.getSingleResult();
            oldStep.setEndDate(new Date());
            em.merge(oldStep);

            // create new step in OrderProcessing
            newStep.setOrderId(order.getOrderId());
            newStep.setStartDate(new Date());
            newStep.setStepEvent(nextStep);
            em.persist(newStep);

            // update information about current step in order
            order.setProcessStep(nextStep);
            order.setStatus(nextStep.getStatus());
            em.merge(order);

            // use new ip
            if (nextStep == OrderStep.SUCCESS) {
                String name = order.getProduct().getName();
                if (name.equals("IPoEUnlim100") || name.equals("IPoEUnlim60") || name.equals("IPoEBasic80")) {
                    ipFilling.fillData(order.getCustomer());
                }
            }

            // send email 'status changed'
            MailManager mm = new MailManager();
            mm.statusChangedEmail(order, getOrderSteps(order));
        }
    }

    @Override
    public void cancelOrder(Order order
    ) {
        if (order.getStatus() != OrderStatus.CLOSED) {
            order.setStatus(OrderStatus.CLOSED);
            em.merge(order);

            OrderProcessing op = new OrderProcessing();
            op.setOrderId(order.getOrderId());
            op.setStartDate(new Date());
            op.setStepEvent(OrderStep.CANCEL);
            em.persist(op);
        }
    }
}
