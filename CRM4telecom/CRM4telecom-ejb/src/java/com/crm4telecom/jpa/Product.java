/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findBySalesPeriod", query = "SELECT p FROM Product p WHERE p.salesPeriod = :salesPeriod"),
    @NamedQuery(name = "Product.findByBaselinePrice", query = "SELECT p FROM Product p WHERE p.baselinePrice = :baselinePrice")})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    private BigDecimal productId;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "SALES_PERIOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salesPeriod;
    @Column(name = "BASELINE_PRICE")
    private BigInteger baselinePrice;
    @OneToMany(mappedBy = "productId")
    private Collection<Orders> ordersCollection;
    @OneToMany(mappedBy = "productId")
    private Collection<CustomerProducts> customerProductsCollection;
    @OneToMany(mappedBy = "productId")
    private Collection<MarketProducts> marketProductsCollection;

    public Product() {
    }

    public Product(BigDecimal productId) {
        this.productId = productId;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        this.productId = productId;
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

    public BigInteger getBaselinePrice() {
        return baselinePrice;
    }

    public void setBaselinePrice(BigInteger baselinePrice) {
        this.baselinePrice = baselinePrice;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    @XmlTransient
    public Collection<CustomerProducts> getCustomerProductsCollection() {
        return customerProductsCollection;
    }

    public void setCustomerProductsCollection(Collection<CustomerProducts> customerProductsCollection) {
        this.customerProductsCollection = customerProductsCollection;
    }

    @XmlTransient
    public Collection<MarketProducts> getMarketProductsCollection() {
        return marketProductsCollection;
    }

    public void setMarketProductsCollection(Collection<MarketProducts> marketProductsCollection) {
        this.marketProductsCollection = marketProductsCollection;
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
        return "com.crm4telecom.entities.Product[ productId=" + productId + " ]";
    }
    
}
