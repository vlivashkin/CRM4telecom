package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(catalog = "")
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

    public MarketsCustomers(Long marketId, Long customerId) {
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
        if (!(object instanceof MarketsCustomers)) {
            return false;
        }
        MarketsCustomers other = (MarketsCustomers) object;
        return (this.marketsCustomersPK != null || other.marketsCustomersPK == null) && (this.marketsCustomersPK == null || this.marketsCustomersPK.equals(other.marketsCustomersPK));
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketsCustomers[ marketsCustomersPK=" + marketsCustomersPK + " ]";
    }

}
