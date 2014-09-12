package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Market_Products")
@Table(catalog = "")
public class MarketProducts implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected MarketProductsPK marketProductsPK;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public MarketProducts() {
    }

    public MarketProducts(MarketProductsPK marketProductsPK) {
        this.marketProductsPK = marketProductsPK;
    }

    public MarketProducts(Long marketId, Long productId) {
        this.marketProductsPK = new MarketProductsPK(marketId, productId);
    }

    public MarketProductsPK getMarketProductsPK() {
        return marketProductsPK;
    }

    public void setMarketProductsPK(MarketProductsPK marketProductsPK) {
        this.marketProductsPK = marketProductsPK;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
        hash += (marketProductsPK != null ? marketProductsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarketProducts)) {
            return false;
        }
        MarketProducts other = (MarketProducts) object;
        if ((this.marketProductsPK == null && other.marketProductsPK != null) || (this.marketProductsPK != null && !this.marketProductsPK.equals(other.marketProductsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.MarketProducts[ marketProductsPK=" + marketProductsPK + " ]";
    }

}
