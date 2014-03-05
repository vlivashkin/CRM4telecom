/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.jpa.Orders;
import java.beans.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;


public class CreateBean implements Serializable {
  private Long OrderId;
    private String status;
    private String priority;
    private Long CustomerId;
    private Long ProductId;
    private Date OrderDate;
    private String type;
    private String TechnicalSupportFlag;
    private Long EmployeeId;
    private String TypeComment;
    private String order;
    private Orders selectedorder;
     @EJB
    private OrderManagerLocal om;

    public Long getOrderId() {
        return OrderId;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public Long getProductId() {
        return ProductId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public String getType() {
        return type;
    }

    public String getTechnicalSupportFlag() {
        return TechnicalSupportFlag;
    }

    public Long getEmployeeId() {
        return EmployeeId;
    }

    public String getTypeComment() {
        return TypeComment;
    }


    public Orders getSelectedorder() {
        return selectedorder;
    }

    
    
    public void setOrderId(Long OrderId) {
        this.OrderId = OrderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCustomerId(Long CustomerId) {
        this.CustomerId = CustomerId;
    }

    public void setProductId(Long ProductId) {
        this.ProductId = ProductId;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTechnicalSupportFlag(String TechnicalSupportFlag) {
        this.TechnicalSupportFlag = TechnicalSupportFlag;
    }

    public void setEmployeeId(Long EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    public void setTypeComment(String TypeComment) {
        this.TypeComment = TypeComment;
    }


    public void setSelectedorder(Orders selectedorder) {
        this.selectedorder = selectedorder;
    }
    
    public String create(){
        om.createNewOrder(OrderId, CustomerId, EmployeeId, ProductId, priority, type, status, OrderDate, TypeComment, type);
        return "index";
    }
    
}
