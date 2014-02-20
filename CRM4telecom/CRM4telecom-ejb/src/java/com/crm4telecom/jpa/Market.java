/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "MARKET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Market.findAll", query = "SELECT m FROM Market m"),
    @NamedQuery(name = "Market.findByMarketId", query = "SELECT m FROM Market m WHERE m.marketId = :marketId"),
    @NamedQuery(name = "Market.findByName", query = "SELECT m FROM Market m WHERE m.name = :name"),
    @NamedQuery(name = "Market.findByDescription", query = "SELECT m FROM Market m WHERE m.description = :description")})
public class Market implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARKET_ID")
    private BigDecimal marketId;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "MARKET_ID", referencedColumnName = "MARKET_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private MarketsCustomers marketsCustomers;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "market1")
    private MarketsCustomers marketsCustomers1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "market")
    private MarketProducts marketProducts;

    public Market() {
    }

    public Market(BigDecimal marketId) {
        this.marketId = marketId;
    }

    public BigDecimal getMarketId() {
        return marketId;
    }

    public void setMarketId(BigDecimal marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MarketsCustomers getMarketsCustomers() {
        return marketsCustomers;
    }

    public void setMarketsCustomers(MarketsCustomers marketsCustomers) {
        this.marketsCustomers = marketsCustomers;
    }

    public MarketsCustomers getMarketsCustomers1() {
        return marketsCustomers1;
    }

    public void setMarketsCustomers1(MarketsCustomers marketsCustomers1) {
        this.marketsCustomers1 = marketsCustomers1;
    }

    public MarketProducts getMarketProducts() {
        return marketProducts;
    }

    public void setMarketProducts(MarketProducts marketProducts) {
        this.marketProducts = marketProducts;
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
        if (!(object instanceof Market)) {
            return false;
        }
        Market other = (Market) object;
        if ((this.marketId == null && other.marketId != null) || (this.marketId != null && !this.marketId.equals(other.marketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.entities.Market[ marketId=" + marketId + " ]";
    }
    
}
