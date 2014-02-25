package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity @Table
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "PRODUCT_ID", nullable = false, precision = 38, scale = 0)
    private Long productId;
    
    @Size(max = 30)
    @Column(length = 30)
    private String name;
    
    @Size(max = 30)
    @Column(length = 30)
    private String description;
    
    @Column(name = "SALES_PERIOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salesPeriod;
    
    @Column(name = "BASELINE_PRICE")
    private Long baselinePrice;
    
    @OneToMany(mappedBy = "productId")
    private Collection<MarketProducts> marketProductsCollection;
    
    @OneToMany(mappedBy = "productId")
    private Collection<Orders> ordersCollection;
    
    @OneToMany(mappedBy = "productId")
    private Collection<CustomerProducts> customerProductsCollection;

    public Product() {
    }

    public Long getProductId() {
        return productId;
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

    public Date getSalesPeriod() {
        return salesPeriod;
    }

    public void setSalesPeriod(Date salesPeriod) {
        this.salesPeriod = salesPeriod;
    }

    public Long getBaselinePrice() {
        return baselinePrice;
    }

    public void setBaselinePrice(Long baselinePrice) {
        this.baselinePrice = baselinePrice;
    }

    public Collection<MarketProducts> getMarketProductsCollection() {
        return marketProductsCollection;
    }

    public void setMarketProductsCollection(Collection<MarketProducts> marketProductsCollection) {
        this.marketProductsCollection = marketProductsCollection;
    }

    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    public Collection<CustomerProducts> getCustomerProductsCollection() {
        return customerProductsCollection;
    }

    public void setCustomerProductsCollection(Collection<CustomerProducts> customerProductsCollection) {
        this.customerProductsCollection = customerProductsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.Product[ productId=" + productId + " ]";
    }
    
}
