package com.crm4telecom.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class MarketProductsPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKET_ID")
    private Long marketId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    private Long productId;

    public MarketProductsPK() {
    }

    public MarketProductsPK(Long marketId, Long productId) {
        this.marketId = marketId;
        this.productId = productId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
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
        if (!(object instanceof MarketProductsPK)) {
            return false;
        }
        MarketProductsPK other = (MarketProductsPK) object;
        if (!this.marketId.equals(other.marketId)) {
            return false;
        }
        return this.productId.equals(other.productId);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketProductsPK[ marketId=" + marketId + ", productId=" + productId + " ]";
    }

}
