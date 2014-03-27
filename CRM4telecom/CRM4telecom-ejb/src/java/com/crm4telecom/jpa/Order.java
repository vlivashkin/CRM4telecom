package com.crm4telecom.jpa;

import com.crm4telecom.enums.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Orders")
@Table
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEC_ORDER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_ORDER", sequenceName = "SEC_ORDER", allocationSize = 1)
    @Column(name = "ORDER_ID", nullable = false, precision = 38, scale = 0)
    private Long orderId;

    @Column(name = "ORDER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "ORDER_TYPE", length = 30)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "COMMENTS", length = 2147483647)
    private String comments;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private OrderPriority priority;

    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne
    private Customer customerId;

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne
    private Product productId;

    @JoinColumn(name = "EMPLOYEE_ID")
    @ManyToOne
    private Employee employeeId;
    
    @Column(name = "TECHNICAL_SUPPORT_FLAG", length = 30)
    private Boolean technicalSupportFlag;

    @Column(name = "PROCESS_STEP")
    @Enumerated(EnumType.STRING)
    private OrderStep processStep;
    
    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus changeOrderStatus(OrderStep event) {
        status = status.nextStatus(event);
        return status;
    }

    public Customer getCustomer() {
        return customerId;
    }

    public Long getCustomerId() {
        if (customerId != null)
            return customerId.getCustomerId();
        return null;
    }

    public void setCustomer(Customer customerId) {
        this.customerId = customerId;
    }

    public Employee getEmployee() {
        return employeeId;
    }

    public Long getEmployeeId() {
        if (employeeId != null)
            return employeeId.getEmployeeId();
        return null;
    }

    public void setEmployee(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public OrderStep getProcessStep() {
        return processStep;
    }

    public void setProcessStep(OrderStep processStep) {
        this.processStep = processStep;
    }

    public Boolean getTechnicalSupportFlag() {
        return technicalSupportFlag;
    }

    public void setTechnicalSupportFlag(Boolean technicalSupportFlag) {
        this.technicalSupportFlag = technicalSupportFlag;
    }

    public Product getProduct() {
        return productId;
    }

    public void setProduct(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        return (this.orderId != null || other.orderId == null) && (this.orderId == null || this.orderId.equals(other.orderId));
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        return "#" + orderId + " " + dateFormat.format(orderDate);
    }

}
