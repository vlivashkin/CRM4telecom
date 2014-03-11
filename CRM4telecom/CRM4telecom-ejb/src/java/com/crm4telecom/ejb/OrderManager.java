package com.crm4telecom.ejb;

import com.crm4telecom.enums.OrderEvent;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderStatus;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import com.crm4telecom.jpa.Product;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class OrderManager implements OrderManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order createOrder(Order order) {
        Date date = new Date();
        order.setOrderDate(date);
        order.setStatus(OrderStatus.NEW);
        em.persist(order);
        OrderProcessing op = new OrderProcessing();
        op.setOrder(order);
        op.setStartDate(date);
        op.setStepEvent(OrderEvent.CREATED);
        em.persist(op);

        return order;
    }

    @Override
    public void modifyOrder(Order order) {
        em.merge(order);
    }

    @Override
    public Order setCustomer(Order order, Long customerId) {
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
    public Long getOrdersCount() {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c   ";

        Query query = em.createQuery(sqlQuery, Order.class);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getOrdersCount(Map<String, String> filters, Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT COUNT(c) FROM Orders c       ";
        if (filters != null && !filters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String filterProperty : filters.keySet()) {
                String filterValue = filters.get(filterProperty);
                sqlQuery += " LOWER( c." + filterProperty + ") like LWER ( \'%" + filterValue + "%\' )  AND";
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
                            sqlQuery += " c.orderDate < CAST( CAST( '" + val.get(0) + "' AS DATE) AS TIMESTAMP)  AND";
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

    private Order fillOrder(Order order, OrderType type, String comments, Long productId, OrderPriority priority, Long managerId) {
        if (productId != null) {
            Product product = em.find(Product.class, productId);
            if (product == null) {
                throw new NoSuchElementException();
            }
            order.setProductId(product);
        }

        order.setOrderType(type);
        order.setComments(comments);
        order.setPriority(priority);
        order.setManagerId(managerId);

        return order;
    }

    @Override
    public OrderStatus getOrderState(Long orderId) {
        Order order = em.find(Order.class, orderId);
        if (order == null) {
            throw new NoSuchElementException();
        }

        return order.getStatus();
    }

    @Override
    public void changeOrderState(Order order, OrderEvent event) {
        OrderProcessing op = new OrderProcessing();
        op.setOrder(order);
        op.setStartDate(new Date());
        op.setStepEvent(event);
        order.changeOrderState(event);
        order.getOrderProcessing().add(op);
        em.persist(op);
        em.merge(order);
    }

    @Override
    public List<Order> search(Map<String, List<String>> parametrs) {
        String sqlQuery = "SELECT c FROM Orders c    ";
        if (!parametrs.isEmpty()) {
            sqlQuery += " WHERE";
            Iterator it = parametrs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                List<String> val = (List<String>) pairs.getValue();
                if (val.size() > 1) {
                    sqlQuery += " ( ";
                    for (String val1 : val) {
                        sqlQuery += " LOWER(c." + pairs.getKey() + ") REGEXP LOWER('" + val1 + "') OR";
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
        return em.createQuery(sqlQuery, Order.class).getResultList();

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
}
