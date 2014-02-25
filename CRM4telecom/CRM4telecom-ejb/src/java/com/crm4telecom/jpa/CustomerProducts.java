package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CUSTOMER_PRODUCTS", catalog = "", schema = "CRM4TELECOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerProducts.findAll", query = "SELECT c FROM CustomerProducts c"),
    @NamedQuery(name = "CustomerProducts.findByCustomerId", query = "SELECT c FROM CustomerProducts c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CustomerProducts.findByStartDate", query = "SELECT c FROM CustomerProducts c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "CustomerProducts.findByEndDate", query = "SELECT c FROM CustomerProducts c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "CustomerProducts.findByPrice", query = "SELECT c FROM CustomerProducts c WHERE c.price = :price")})
public class CustomerProducts implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    
    @NotNull
    @Column(name = "CUSTOMER_ID", nullable = false, precision = 38, scale = 0)
    private Long customerId;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    private Long price;
    
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne
    private Product productId;

    public CustomerProducts() {
    }

    public CustomerProducts(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerProducts)) {
            return false;
        }
        CustomerProducts other = (CustomerProducts) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.CustomerProducts[ customerId=" + customerId + " ]";
    }
    
}
