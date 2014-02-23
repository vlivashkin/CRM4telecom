package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity @Table
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMAIL")
    private String email;
    
    @Size(max = 20)
    @Column(name = "CARD_NUMBER")
    private String cardNumber;
    
    @Column(name = "CARD_EXP_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cardExpData;
    
    @Column(name = "BALANCE")
    private Long balance;
    
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private CustomerProducts customerProducts;
    
    @OneToMany(mappedBy = "customerId")
    private Collection<Orders> ordersCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<PhoneNumber> phoneNumberCollection;
    
    @OneToMany(mappedBy = "customerId")
    private Collection<MarketsCustomers> marketsCustomersCollection;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String cardNumber, Date cardExpData, Long balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cardNumber = cardNumber;
        this.cardExpData = cardExpData;
        this.balance = balance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getCardExpData() {
        return cardExpData;
    }

    public void setCardExpData(Date cardExpData) {
        this.cardExpData = cardExpData;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public CustomerProducts getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(CustomerProducts customerProducts) {
        this.customerProducts = customerProducts;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    @XmlTransient
    public Collection<PhoneNumber> getPhoneNumberCollection() {
        return phoneNumberCollection;
    }

    public void setPhoneNumberCollection(Collection<PhoneNumber> phoneNumberCollection) {
        this.phoneNumberCollection = phoneNumberCollection;
    }

    @XmlTransient
    public Collection<MarketsCustomers> getMarketsCustomersCollection() {
        return marketsCustomersCollection;
    }

    public void setMarketsCustomersCollection(Collection<MarketsCustomers> marketsCustomersCollection) {
        this.marketsCustomersCollection = marketsCustomersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[customerId=" + customerId + "], " + firstName + " " + lastName;
    }
    
}
