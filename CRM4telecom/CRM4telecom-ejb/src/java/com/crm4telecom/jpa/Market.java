package com.crm4telecom.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(catalog = "")
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marketId != null ? marketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Market)) {
            return false;
        }
        Market other = (Market) object;
        return this.marketId.equals(other.marketId);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.Market[ marketId=" + marketId + " ]";
    }

}
