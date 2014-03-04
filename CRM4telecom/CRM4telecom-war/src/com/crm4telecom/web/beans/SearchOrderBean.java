/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Orders;
import com.crm4telecom.web.beans.util.LazyCustomerDataModel;
import java.beans.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Alex
 */
public class SearchOrderBean implements Serializable {

    @EJB
    private OrderManagerLocal om;
    private Long OrderId;
    private List<String> status;
    private List<String> priority;
    private Long CustomerId;
    private Long ManagerId;
    private Long ProductId;
    private Date OrderDate;
    private List<String> type;
    private String TechnicalSupportFlag;
    private Long EmployeeId;
    private String TypeComment;
    private List<Orders> order;
    private Orders selectedorder;
    private Map<String, List<String>> parametrs;

    public OrderManagerLocal getOm() {
        return om;
    }

    @PostConstruct
    public void init() {
        parametrs = new HashMap();
    }

    
    
    public void setOm(OrderManagerLocal om) {
        this.om = om;
    }

    public Orders getSelectedorder() {
        return selectedorder;
    }

    public Long getOrderId() {
        return OrderId;
    }

    public Long getEmployeeId() {
        return EmployeeId;
    }

    public void setOrderId(Long OrderId) {
        this.OrderId = OrderId;
    }

    public void setProductId(Long ProductId) {
        this.ProductId = ProductId;
    }

    public void setEmployeeId(Long EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    public void setSelectedorder(Orders selectedorder) {
        this.selectedorder = selectedorder;
    }

    public List<Orders> getOrder() {
        return order;
    }

    public void setOrder(List<Orders> order) {
        this.order = order;
    }

    public List<String> getStatus() {
        return status;
    }

    public List<String> getPriority() {
        return priority;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }
    

    public Date getOrderDate() {
        return OrderDate;
    }

    public List<String> getType() {
        return type;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public void setPriority(List<String> priority) {
        this.priority = priority;
    }

    public void setCustomerId(Long CustomerId) {
        this.CustomerId = CustomerId;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String search() {
        if (OrderId != null) {
            List<String> order = new ArrayList();
            order.add(OrderId.toString());
            parametrs.put("orderId", order);
        }
        if (CustomerId != null) {
            List<String> cust = new ArrayList();
            cust.add(CustomerId.toString());
            parametrs.put("customerId", cust);
        }
        if (EmployeeId != null) {
            List<String> emp = new ArrayList();
            emp.add(EmployeeId.toString());
            parametrs.put("employeeId", emp);
        }
        if (priority.size() != 0) {
            parametrs.put("priority", priority);
        }

        if (status.size() != 0) {
            parametrs.put("status", status);
        }

        if (type.size() != 0) {
            parametrs.put("orderType", type);
        }
        
       if (OrderDate != null){
           List<String> date = new ArrayList();
           System.out.println(OrderDate.toString());
           System.out.println(OrderDate.toString().substring(0,10)+"aaa");
           
           Timestamp ts = new Timestamp(OrderDate.getTime());
           System.out.println(ts.toString().substring(0, 10));
           String y = ts.toString().substring(0,4);
           String m = OrderDate.toString().substring(4,7 ).toUpperCase();
           String d = ts.toString().substring(8,10);
           System.out.println(d+" "+m+" "+y);
           date.add(d+"-"+m+"-"+y);
           System.out.println(date);
           parametrs.put("orderDate",date);
       } 
        order = om.search(parametrs);
        
        
        return "search_order";
    }

}
