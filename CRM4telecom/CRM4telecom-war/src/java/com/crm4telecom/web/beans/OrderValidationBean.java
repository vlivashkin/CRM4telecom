package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.jpa.Orders;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ManagedBean
@RequestScoped
public class OrderValidationBean implements Serializable {

    @EJB
    private CustomerManagerLocal cm;

    private Long customerId;

    private String comment;

    @Enumerated(EnumType.STRING)
    private OrderPriority priority;

    private Long managerId;

    private Long employeeId;

    private Long productId;

    private Boolean technicalSupportFlag;

    public void init(Orders order) {
        if (order != null) {
            if (order.getCustomerId() != null) {
                customerId = order.getCustomerId().getCustomerId();
            }
            comment = order.getTypeComment();
            if (order.getPriority() != null) {
                priority = OrderPriority.valueOf(order.getPriority());
            }
            managerId = order.getManagerId();
            employeeId = order.getEmployeeId();
            if (order.getProductId() != null) {
                productId = order.getProductId().getProductId();
            }
            if (order.getTechnicalSupportFlag() != null) {
                technicalSupportFlag = Boolean.valueOf(order.getTechnicalSupportFlag());
            }
        }
    }

    public void fillOrder(Orders order) {
        System.out.println("'" + customerId + "'");
        order.setCustomerId(customerId == null ? null : cm.getCustomer(customerId));
        order.setTypeComment(comment);
        order.setPriority(priority.name());
        order.setManagerId(managerId);
        order.setEmployeeId(employeeId);
        order.setProductId(null);
        order.setTechnicalSupportFlag(technicalSupportFlag.toString());
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getTechnicalSupportFlag() {
        return technicalSupportFlag;
    }

    public void setTechnicalSupportFlag(Boolean technicalSupportFlag) {
        this.technicalSupportFlag = technicalSupportFlag;
    }

}
