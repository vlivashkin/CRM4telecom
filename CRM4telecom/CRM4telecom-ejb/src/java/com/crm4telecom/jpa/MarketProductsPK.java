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
public class MarketProductsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKET_ID")
    private BigInteger marketId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    private BigInteger productId;

    public MarketProductsPK() {
    }

    public MarketProductsPK(BigInteger marketId, BigInteger productId) {
        this.marketId = marketId;
        this.productId = productId;
    }

    public BigInteger getMarketId() {
        return marketId;
    }

    public void setMarketId(BigInteger marketId) {
        this.marketId = marketId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marketId != null ? marketId.hashCode() : 0);
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarketProductsPK)) {
            return false;
        }
        MarketProductsPK other = (MarketProductsPK) object;
        if ((this.marketId == null && other.marketId != null) || (this.marketId != null && !this.marketId.equals(other.marketId))) {
            return false;
        }
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketProductsPK[ marketId=" + marketId + ", productId=" + productId + " ]";
    }
    
}
