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
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "CUSTOMER_ID", nullable = false, precision = 38, scale = 0)
    private Long customerId;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "FIRST_NAME", nullable = false, length = 30)
    private String firstName;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LAST_NAME", nullable = false, length = 30)
    private String lastName;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String email;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String street;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String house;
    
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String apartment;
    
    @Size(max = 20)
    @Column(name = "CARD_NUMBER", length = 20)
    private String cardNumber;
    
    @Column(name = "CARD_EXP_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cardExpData;
    
    private Long balance;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<PhoneNumber> phoneNumberCollection;
    
    @OneToMany(mappedBy = "customerId")
    private Collection<MarketsCustomers> marketsCustomersCollection;

    public Customer() {
    }

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    public Customer(Long customerId, String firstName, String lastName, String email, String street, String house, String apartment) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
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

    public Collection<PhoneNumber> getPhoneNumberCollection() {
        return phoneNumberCollection;
    }

    public void setPhoneNumberCollection(Collection<PhoneNumber> phoneNumberCollection) {
        this.phoneNumberCollection = phoneNumberCollection;
    }

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
        return "com.crm4telecom.jpa.Customer[ customerId=" + customerId + " ]";
    }
    
}
