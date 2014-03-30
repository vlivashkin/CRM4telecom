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
public class MarketsCustomers implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MarketsCustomersPK marketsCustomersPK;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public MarketsCustomers() {
    }

    public MarketsCustomers(MarketsCustomersPK marketsCustomersPK) {
        this.marketsCustomersPK = marketsCustomersPK;
    }

    public MarketsCustomers(BigInteger marketId, BigInteger customerId) {
        this.marketsCustomersPK = new MarketsCustomersPK(marketId, customerId);
    }

    public MarketsCustomersPK getMarketsCustomersPK() {
        return marketsCustomersPK;
    }

    public void setMarketsCustomersPK(MarketsCustomersPK marketsCustomersPK) {
        this.marketsCustomersPK = marketsCustomersPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marketsCustomersPK != null ? marketsCustomersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarketsCustomers)) {
            return false;
        }
        MarketsCustomers other = (MarketsCustomers) object;
        if ((this.marketsCustomersPK == null && other.marketsCustomersPK != null) || (this.marketsCustomersPK != null && !this.marketsCustomersPK.equals(other.marketsCustomersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketsCustomers[ marketsCustomersPK=" + marketsCustomersPK + " ]";
    }
    
}
