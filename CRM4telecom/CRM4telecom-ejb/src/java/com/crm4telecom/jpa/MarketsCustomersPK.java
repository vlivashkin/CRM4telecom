package com.crm4telecom.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class MarketsCustomersPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKET_ID")
    private Long marketId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    public MarketsCustomersPK() {
    }

    public MarketsCustomersPK(Long marketId, Long customerId) {
        this.marketId = marketId;
        this.customerId = customerId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marketId != null ? marketId.hashCode() : 0);
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MarketsCustomersPK)) {
            return false;
        }
        MarketsCustomersPK other = (MarketsCustomersPK) object;
        if (!this.marketId.equals(other.marketId)) {
            return false;
        }
        return this.customerId.equals(other.customerId);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketsCustomersPK[ marketId=" + marketId + ", customerId=" + customerId + " ]";
    }

}
