/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.stub.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Василий
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByProductId", query = "SELECT p FROM Products p WHERE p.productId = :productId"),
    @NamedQuery(name = "Products.findByOnetimePrice", query = "SELECT p FROM Products p WHERE p.onetimePrice = :onetimePrice"),
    @NamedQuery(name = "Products.findByMonthlyPrice", query = "SELECT p FROM Products p WHERE p.monthlyPrice = :monthlyPrice")})
public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCT_ID")
    private BigDecimal productId;
    @Column(name = "ONETIME_PRICE")
    private BigInteger onetimePrice;
    @Column(name = "MONTHLY_PRICE")
    private BigInteger monthlyPrice;
    @ManyToMany(mappedBy = "productsList")
    private List<Customers> customersList;

    public Products() {
    }

    public Products(BigDecimal productId) {
        this.productId = productId;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        this.productId = productId;
    }

    public BigInteger getOnetimePrice() {
        return onetimePrice;
    }

    public void setOnetimePrice(BigInteger onetimePrice) {
        this.onetimePrice = onetimePrice;
    }

    public BigInteger getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigInteger monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @XmlTransient
    public List<Customers> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(List<Customers> customersList) {
        this.customersList = customersList;
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
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.stub.jpa.Products[ productId=" + productId + " ]";
    }
    
}
