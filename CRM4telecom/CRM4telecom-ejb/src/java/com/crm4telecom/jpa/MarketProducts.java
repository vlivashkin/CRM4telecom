/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "MARKET_PRODUCTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarketProducts.findAll", query = "SELECT m FROM MarketProducts m"),
    @NamedQuery(name = "MarketProducts.findByMarketId", query = "SELECT m FROM MarketProducts m WHERE m.marketId = :marketId"),
    @NamedQuery(name = "MarketProducts.findByPrice", query = "SELECT m FROM MarketProducts m WHERE m.price = :price"),
    @NamedQuery(name = "MarketProducts.findByStartDate", query = "SELECT m FROM MarketProducts m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "MarketProducts.findByEndDate", query = "SELECT m FROM MarketProducts m WHERE m.endDate = :endDate")})
public class MarketProducts implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKET_ID")
    private BigDecimal marketId;
    @Column(name = "PRICE")
    private BigInteger price;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne
    private Product productId;
    @JoinColumn(name = "MARKET_ID", referencedColumnName = "MARKET_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Market market;

    public MarketProducts() {
    }

    public MarketProducts(BigDecimal marketId) {
        this.marketId = marketId;
    }

    public BigDecimal getMarketId() {
        return marketId;
    }

    public void setMarketId(BigDecimal marketId) {
        this.marketId = marketId;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
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

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marketId != null ? marketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarketProducts)) {
            return false;
        }
        MarketProducts other = (MarketProducts) object;
        if ((this.marketId == null && other.marketId != null) || (this.marketId != null && !this.marketId.equals(other.marketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.entities.MarketProducts[ marketId=" + marketId + " ]";
    }
    
}
