package com.crm4telecom.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(catalog = "", schema = "CRM4TELECOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Market.findAll", query = "SELECT m FROM Market m"),
    @NamedQuery(name = "Market.findByMarketId", query = "SELECT m FROM Market m WHERE m.marketId = :marketId"),
    @NamedQuery(name = "Market.findByName", query = "SELECT m FROM Market m WHERE m.name = :name"),
    @NamedQuery(name = "Market.findByDescription", query = "SELECT m FROM Market m WHERE m.description = :description")})
public class Market implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    
    @NotNull
    @Column(name = "MARKET_ID", nullable = false, precision = 38, scale = 0)
    private Long marketId;
    @Size(max = 30)
    @Column(length = 30)
    private String name;
    @Size(max = 30)
    @Column(length = 30)
    private String description;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "market")
    private MarketProducts marketProducts;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "market")
    private MarketsCustomers marketsCustomers;

    public Market() {
    }

    public Market(Long marketId) {
        this.marketId = marketId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
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

    public MarketProducts getMarketProducts() {
        return marketProducts;
    }

    public void setMarketProducts(MarketProducts marketProducts) {
        this.marketProducts = marketProducts;
    }

    public MarketsCustomers getMarketsCustomers() {
        return marketsCustomers;
    }

    public void setMarketsCustomers(MarketsCustomers marketsCustomers) {
        this.marketsCustomers = marketsCustomers;
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
        return "com.crm4telecom.jpa.Market[ marketId=" + marketId + " ]";
    }
    
}
