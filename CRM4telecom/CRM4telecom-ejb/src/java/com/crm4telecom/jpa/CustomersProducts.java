package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Customers_Products")
@Table(catalog = "")
public class CustomersProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected CustomerProductsPK customersProductsPK;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "PRICE")
    private Long price;

    public CustomersProducts() {
    }

    public CustomersProducts(CustomerProductsPK customerProductsPK) {
        this.customersProductsPK = customerProductsPK;
    }

    public CustomersProducts(Long customerId, Long productId) {
        this.customersProductsPK = new CustomerProductsPK(customerId, productId);
    }

    public CustomerProductsPK getCustomerProductsPK() {
        return customersProductsPK;
    }

    public void setCustomerProductsPK(CustomerProductsPK customerProductsPK) {
        this.customersProductsPK = customerProductsPK;
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
        hash += (customersProductsPK != null ? customersProductsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomersProducts)) {
            return false;
        }
        CustomersProducts other = (CustomersProducts) object;
        return (this.customersProductsPK != null || other.customersProductsPK == null) && (this.customersProductsPK == null || this.customersProductsPK.equals(other.customersProductsPK));
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.CustomerProducts[ customerProductsPK=" + customersProductsPK + " ]";
    }

}
