/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex
 */
@Embeddable
public class MarketsCustomersPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKET_ID")
    private BigInteger marketId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMER_ID")
    private BigInteger customerId;

    public MarketsCustomersPK() {
    }

    public MarketsCustomersPK(BigInteger marketId, BigInteger customerId) {
        this.marketId = marketId;
        this.customerId = customerId;
    }

    public BigInteger getMarketId() {
        return marketId;
    }

    public void setMarketId(BigInteger marketId) {
        this.marketId = marketId;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customerId) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarketsCustomersPK)) {
            return false;
        }
        MarketsCustomersPK other = (MarketsCustomersPK) object;
        if ((this.marketId == null && other.marketId != null) || (this.marketId != null && !this.marketId.equals(other.marketId))) {
            return false;
        }
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketsCustomersPK[ marketId=" + marketId + ", customerId=" + customerId + " ]";
    }
    
}
