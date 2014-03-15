package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.ProductManagerLocal;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.ProductsName;
import com.crm4telecom.jpa.Order;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ManagedBean
@SessionScoped
public class OrderValidationBean implements Serializable {

    @EJB
    private CustomerManagerLocal cm;
    
    @EJB
    private ProductManagerLocal pm;

    private Long customerId;

    private String comments;

    @Enumerated(EnumType.STRING)
    private OrderPriority priority;
    
    @Enumerated(EnumType.STRING)
    private ProductsName product;

    private Long managerId;

    private Long employeeId;

    private Long productId;

    private Boolean technicalSupportFlag;

    public void init(Order order) {
        if (order != null) {
            if (order.getCustomerId() != null) {
                customerId = order.getCustomerId().getCustomerId();
            }
            comments = order.getComments();
            if (order.getPriority() != null) {
                priority = order.getPriority();
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

    public void fillOrder(Order order) {
        order.setCustomerId(customerId == null ? null : cm.getCustomer(customerId));
        order.setComments(comments);
        order.setPriority(priority);
        order.setManagerId(managerId);
        order.setEmployeeId(employeeId);
        order.setProductId(pm.getProduct(pm.getProductId(product)));
        order.setTechnicalSupportFlag(technicalSupportFlag.toString());
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comments;
    }

    public void setComment(String comment) {
        this.comments = comment;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public ProductsName getProduct() {
        return product;
    }

    public void setProduct(ProductsName product) {
        this.product = product;
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
