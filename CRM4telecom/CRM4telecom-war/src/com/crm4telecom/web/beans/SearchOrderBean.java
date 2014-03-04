/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Orders;
import java.beans.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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

    public OrderManagerLocal getOm() {
        return om;
    }

    public void setOm(OrderManagerLocal om) {
        this.om = om;
    }

    public Orders getSelectedorder() {
        return selectedorder;
    }

    public void setSelectedorder(Orders selectedorder) {
        this.selectedorder = selectedorder;
    }

    public List<Orders> getOrder() {
        System.out.println("idisdsdasd+++++===="+om.getAllOrders().get(0).getStatus());
        return om.getAllOrders();
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

}
