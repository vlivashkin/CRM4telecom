package com.crm4telecom.ejb;

import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.ejb.util.OrderType;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.jpa.Product;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OrderManager implements OrderManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Orders createOrder(Orders order) {
        Date date = new Date();
        order.setOrderDate(date);
        order.setStatus(OrderState.NEW.name());
        em.persist(order);
        OrderProcessing op = new OrderProcessing(order.getOrderId());
        op.setStartDate(date);
        op.setStepName(OrderEvent.CREATED.name());
        em.persist(op);
        order.setOrderProcessing(op);
        em.persist(order);

        return order;
    }

    @Override
    public void modifyOrder(Orders order) {
        em.merge(order);
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
    public List<Orders> getOrdersList(int first, int pageSize, String sortField, String sortOrder, Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT c FROM Orders c        ";
        System.out.println("size ===" + parametrs.size());
        if (!parametrs.isEmpty()) {
            sqlQuery += " WHERE";
            for (String paramProperty : parametrs.keySet()) {
                List<String> val = (List<String>) parametrs.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (int i = 0; i < val.size(); i++) {
                        sqlQuery += " LOWER(c." + paramProperty + ") REGEXP LOWER('" + val.get(i) + "') OR";

                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    String check = (String) paramProperty;
                    if (check.compareTo("fromDate") != 0 && check.compareTo("toDate") != 0) {
                        if (check.compareTo("customerId") == 0) {
                            
                            sqlQuery += "  LOWER ( c.customerId.customerId )  REGEXP LOWER ('" + val.get(0) + "') AND";
                        } else {
                            sqlQuery += "   LOWER( c." + paramProperty + " ) REGEXP LOWER('" + val.get(0) + "')   AND";
                        }
                    } else {
                        if (check.compareTo("fromDate") == 0) {
                            sqlQuery += " c.orderDate > CAST(CAST( '" + val.get(0) + "' AS DATE ) AS TIMESTAMP)     AND";
                        }
                        if (check.compareTo("toDate") == 0) {
                            sqlQuery += " c.orderDate < CAST( CAST( '" + val.get(0) + "' AS DATE) AS TIMESTAMP)    AND";
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

        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        Query query = em.createQuery(sqlQuery, Orders.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        System.out.println(sqlQuery);
        return query.getResultList();
    }

    @Override
    public Long getOrdersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c   ";

        Query query = em.createQuery(sqlQuery, Orders.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getOrdersCount(Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c       ";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " c." + filterProperty + " like \'" + filterValue + "%\' AND";
            }
            System.out.println("filterasasd");

        }
        if (!parametrs.isEmpty()) {
            if (filters.isEmpty()) {
                sqlQuery += " WHERE";
                
            }
            for (String paramProperty : parametrs.keySet()) {
                List<String> val = (List<String>) parametrs.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (int i = 0; i < val.size(); i++) {
                        sqlQuery += "   LOWER( c." + paramProperty + "  ) REGEXP LOWER('" + val.get(i) + "') OR";

                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    String check = (String) paramProperty;
                    if (check.compareTo("fromDate") != 0 && check.compareTo("toDate") != 0) {
                        if (check.compareTo("customerId") == 0) {
                            sqlQuery += "  LOWER ( c.customerId.customerId )  REGEXP LOWER ('" + val.get(0) + "') AND";
                        } else {
                            sqlQuery += " LOWER( c." + paramProperty + " ) REGEXP LOWER('" + val.get(0) + "')   AND";
                        }
                    } else {
                        if (check.compareTo("fromDate") == 0) {
                            sqlQuery += " c.orderDate > CAST(CAST( '" + val.get(0) + "' AS DATE ) AS TIMESTAMP)  AND";
                        }
                        if (check.compareTo("toDate") == 0) {
                            sqlQuery += " c.orderDate < CAST( CAST( '" + val.get(0) + "' AS DATE) AS TIMESTAMP)  AND";
                        }
                    }
                }

            }

        }

        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        System.out.println(sqlQuery);
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
    public void changeOrderState(Orders order, OrderEvent event) {
        order.changeOrderState(event);
        em.merge(order);
        OrderProcessing op = order.getOrderProcessing();
        op.setStartDate(new Date());
        op.setStepName(event.name());
        em.persist(op);
    }

    @Override
    public List<Orders> search(Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT c FROM Orders c    ";
        if (!parametrs.isEmpty()) {
            sqlQuery += " WHERE";
            Iterator it = parametrs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                List<String> val = (List<String>) pairs.getValue();
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (int i = 0; i < val.size(); i++) {
                        sqlQuery += " LOWER(c." + pairs.getKey() + ") REGEXP LOWER('" + val.get(i) + "') OR";

                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - "OR".length());
                    sqlQuery += " ) AND";
                } else {
                    String check = (String) pairs.getKey();
                    if (check.compareTo("orderDate") != 0) {
                        sqlQuery += " LOWER(c." + pairs.getKey() + ") REGEXP LOWER('" + val.get(0) + "') AND";
                    } else {
                        sqlQuery += " c.orderDate > CAST(CAST( '" + val.get(0) + "' AS DATE ) AS TIMESTAMP)" + " AND c.orderDate < CAST( CAST( '" + val.get(0) + "' AS DATE) AS TIMESTAMP)+1 AND";
                    }
                }

                it.remove();
            }
        }
        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
        System.out.println(sqlQuery);
        return em.createQuery(sqlQuery, Orders.class).getResultList();

    }

    @Override
    public List<String> completeOrder(String rawOrder) {
        List<String> orders = new ArrayList<>();
        String raw = rawOrder.trim();

        if (raw.matches("^\\d+$")) {
            Long id = Long.parseLong(raw);
            Orders ord = em.find(Orders.class, id);
            if (ord != null) {
                orders.add(ord.toString());
            }
        } else {
            System.out.println("stroka " + raw);
        }

        return orders;
    }
}
