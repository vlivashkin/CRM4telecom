package com.crm4telecom.jpa;

import com.crm4telecom.enums.CustomerStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Customers")
@Table(catalog = "")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEC_CUSTOMER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_CUSTOMER", sequenceName = "SEC_CUSTOMER", allocationSize = 1)
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
    private String street;

    @NotNull
    @Column(nullable = false)
    private Long building;

    @NotNull
    @Column(nullable = false)
    private Long flat;

    @Size(max = 20)
    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;

    @Column(length = 30)
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Column(name = "STATUS_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusUpdateDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<PhoneNumber> phoneNumberList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<StaticIp> staticIpList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<Order> ordersList;

    @JoinTable(name = "MARKETS_CUSTOMERS", joinColumns = {
        @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "MARKET_ID", referencedColumnName = "MARKET_ID")})
    @ManyToMany
    private List<Market> marketsList;

    @JoinTable(name = "CUSTOMERS_PRODUCTS", joinColumns = {
        @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")})
    @ManyToMany
    private List<Product> productsList;

    public Customer() {
    }

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    public Customer(Long customerId, String firstName, String lastName, String email, String street, Long building, Long flat) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.building = building;
        this.flat = flat;
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

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
        this.building = building;
    }

    public Long getFlat() {
        return flat;
    }

    public void setFlat(Long flat) {
        this.flat = flat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Date getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public void setStatusUpdateDate(Date statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumberList;
    }

    public List<StaticIp> getStaticIps() {
        return staticIpList;
    }

    public List<Order> getOrders() {
        return ordersList;
    }

    public List<Market> getMarkets() {
        return marketsList;
    }

    public List<Product> getProducts() {
        return productsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return (this.customerId != null || other.customerId == null) && (this.customerId == null || this.customerId.equals(other.customerId));
    }

    @Override
    public String toString() {
        return "#" + customerId + " " + firstName + " " + lastName;
    }
}
