/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ORDERS")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orders.findByOrderDate", query = "SELECT o FROM Orders o WHERE o.orderDate = :orderDate"),
    @NamedQuery(name = "Orders.findByOrderType", query = "SELECT o FROM Orders o WHERE o.orderType = :orderType"),
    @NamedQuery(name = "Orders.findByTypeComment", query = "SELECT o FROM Orders o WHERE o.typeComment = :typeComment"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByPriority", query = "SELECT o FROM Orders o WHERE o.priority = :priority"),
    @NamedQuery(name = "Orders.findByManagerId", query = "SELECT o FROM Orders o WHERE o.managerId = :managerId"),
    @NamedQuery(name = "Orders.findByTechnicalSupportFlag", query = "SELECT o FROM Orders o WHERE o.technicalSupportFlag = :technicalSupportFlag")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "ORDER_ID")
    private Long orderId;
    
    @Column(name = "ORDER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
    @Size(max = 30)
    @Column(name = "ORDER_TYPE")
    private String orderType;
    
    @Size(max = 30)
    @Column(name = "TYPE_COMMENT")
    private String typeComment;
    
    @Size(max = 30)
    @Column(name = "STATUS")
    private String status;
    
    @Size(max = 30)
    @Column(name = "PRIORITY")
    private String priority;
    
    @Column(name = "MANAGER_ID")
    private Long managerId;
    
    @Size(max = 30)
    @Column(name = "TECHNICAL_SUPPORT_FLAG")
    private String technicalSupportFlag;
    
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne
    private Product productId;
    
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private OrderProcessing orderProcessing;
    
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne
    private Employee employeeId;
    
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @ManyToOne
    private Customer customerId;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orders1")
    private OrderProcessing orderProcessing1;

    public Orders() {
    }

    public Orders(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTypeComment() {
        return typeComment;
    }

    public void setTypeComment(String typeComment) {
        this.typeComment = typeComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getTechnicalSupportFlag() {
        return technicalSupportFlag;
    }

    public void setTechnicalSupportFlag(String technicalSupportFlag) {
        this.technicalSupportFlag = technicalSupportFlag;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public OrderProcessing getOrderProcessing() {
        return orderProcessing;
    }

    public void setOrderProcessing(OrderProcessing orderProcessing) {
        this.orderProcessing = orderProcessing;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public OrderProcessing getOrderProcessing1() {
        return orderProcessing1;
    }

    public void setOrderProcessing1(OrderProcessing orderProcessing1) {
        this.orderProcessing1 = orderProcessing1;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.entities.Orders[ orderId=" + orderId + " ]";
    }
    
}
