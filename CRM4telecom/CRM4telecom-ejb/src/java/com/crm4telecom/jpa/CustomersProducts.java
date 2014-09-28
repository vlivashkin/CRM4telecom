package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity(name="Customers_Products")
@Table(catalog = "")
public class CustomersProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected CustomersProductsPK customerProductsPK;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "PRICE")
    private Long price;
    
    @Column(name = "CUSTOMER_ID", nullable = false, precision = 38, scale = 0)
    private Long customerId;
    
    @NotNull
    @Column(name = "PRODUCT_ID", nullable = false, precision = 22, scale = 0)
    private Long productId;

    public CustomersProducts() {
    }

    public CustomersProducts(CustomersProductsPK customerProductsPK) {
        this.customerProductsPK = customerProductsPK;
    }

    public CustomersProducts(Long customerId, Long productId) {
        this.customerProductsPK = new CustomersProductsPK(customerId, productId);
    }

    public CustomersProductsPK getCustomerProductsPK() {
        return customerProductsPK;
    }

    public void setCustomerProductsPK(CustomersProductsPK customerProductsPK) {
        this.customerProductsPK = customerProductsPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerProductsPK != null ? customerProductsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomersProducts)) {
            return false;
        }
        CustomersProducts other = (CustomersProducts) object;
        return (this.customerProductsPK != null || other.customerProductsPK == null) && (this.customerProductsPK == null || this.customerProductsPK.equals(other.customerProductsPK));
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.CustomerProducts[ customerProductsPK=" + customerProductsPK + " ]";
    }

}
