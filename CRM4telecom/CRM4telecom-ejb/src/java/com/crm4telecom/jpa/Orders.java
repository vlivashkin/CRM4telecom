package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(catalog = "", schema = "CRM4TELECOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orders.findByOrderDate", query = "SELECT o FROM Orders o WHERE o.orderDate = :orderDate"),
    @NamedQuery(name = "Orders.findByOrderType", query = "SELECT o FROM Orders o WHERE o.orderType = :orderType"),
    @NamedQuery(name = "Orders.findByTypeComment", query = "SELECT o FROM Orders o WHERE o.typeComment = :typeComment"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByPriority", query = "SELECT o FROM Orders o WHERE o.priority = :priority"),
    @NamedQuery(name = "Orders.findByEmployeeId", query = "SELECT o FROM Orders o WHERE o.employeeId = :employeeId"),
    @NamedQuery(name = "Orders.findByManagerId", query = "SELECT o FROM Orders o WHERE o.managerId = :managerId"),
    @NamedQuery(name = "Orders.findByTechnicalSupportFlag", query = "SELECT o FROM Orders o WHERE o.technicalSupportFlag = :technicalSupportFlag")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "SEC_ORDER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_ORDER", sequenceName = "SEC_ORDER", allocationSize=1)
    @Column(name = "ORDER_ID", nullable = false, precision = 38, scale = 0)
    private Long orderId;
    
    @Column(name = "ORDER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
    @Size(max = 30)
    @Column(name = "ORDER_TYPE", length = 30)
    private String orderType;
    
    @Size(max = 30)
    @Column(name = "TYPE_COMMENT", length = 30)
    private String typeComment;
    
    @Size(max = 30)
    @Column(length = 30)
    private String status;
    
    @Size(max = 30)
    @Column(length = 30)
    private String priority;
    
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;
    
    @Column(name = "MANAGER_ID")
    private Long managerId;
    
    @Size(max = 30)
    @Column(name = "TECHNICAL_SUPPORT_FLAG", length = 30)
    private String technicalSupportFlag;
    
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne
    private Product productId;
    
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @ManyToOne
    private Customer customerId;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orders")
    private OrderProcessing orderProcessing;

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public OrderProcessing getOrderProcessing() {
        return orderProcessing;
    }

    public void setOrderProcessing(OrderProcessing orderProcessing) {
        this.orderProcessing = orderProcessing;
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
        return "com.crm4telecom.jpa.Orders[ orderId=" + orderId + " ]";
    }
    
}
