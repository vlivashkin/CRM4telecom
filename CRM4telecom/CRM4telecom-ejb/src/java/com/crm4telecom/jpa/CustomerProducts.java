/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(catalog = "", schema = "CRM4TELECOM")
public class CustomerProducts implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CustomerProductsPK customerProductsPK;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "PRICE")
    private BigInteger price;

    public CustomerProducts() {
    }

    public CustomerProducts(CustomerProductsPK customerProductsPK) {
        this.customerProductsPK = customerProductsPK;
    }

    public CustomerProducts(Long customerId, Long productId) {
        this.customerProductsPK = new CustomerProductsPK(customerId, productId);
    }

    public CustomerProductsPK getCustomerProductsPK() {
        return customerProductsPK;
    }

    public void setCustomerProductsPK(CustomerProductsPK customerProductsPK) {
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

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerProducts)) {
            return false;
        }
        CustomerProducts other = (CustomerProducts) object;
        if ((this.customerProductsPK == null && other.customerProductsPK != null) || (this.customerProductsPK != null && !this.customerProductsPK.equals(other.customerProductsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.CustomerProducts[ customerProductsPK=" + customerProductsPK + " ]";
    }
    
}
