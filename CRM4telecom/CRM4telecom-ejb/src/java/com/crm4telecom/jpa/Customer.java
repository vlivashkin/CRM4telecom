package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(catalog = "", schema = "CRM4TELECOM")
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
    private String email;

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
    @Column(name = "CARD_NUMBER", length = 20)
    private String cardNumber;

    @Column(name = "CONNECTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date connectionDate;

    @Size(max = 30)
    @Column(length = 30)
    private String status;

    @Column(name = "STATUS_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusUpdateDate;

    @Column(name = "CARD_EXP_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cardExpData;

    private Long balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private List<PhoneNumber> phoneNumberList;

    @OneToMany(mappedBy = "customerId")
    private List<StaticIp> staticIpList;

    @OneToMany(mappedBy = "customerId")
    private List<Orders> ordersList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private BalanceHistory balanceHistory;

    @OneToMany(mappedBy = "customerId")
    private List<MarketsCustomers> marketsCustomersList;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(Date connectionDate) {
        this.connectionDate = connectionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public void setStatusUpdateDate(Date statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
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

    @XmlTransient
    public List<PhoneNumber> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    @XmlTransient
    public List<StaticIp> getStaticIpList() {
        return staticIpList;
    }

    public void setStaticIpList(List<StaticIp> staticIpList) {
        this.staticIpList = staticIpList;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public BalanceHistory getBalanceHistory() {
        return balanceHistory;
    }

    public void setBalanceHistory(BalanceHistory balanceHistory) {
        this.balanceHistory = balanceHistory;
    }

    @XmlTransient
    public List<MarketsCustomers> getMarketsCustomersList() {
        return marketsCustomersList;
    }

    public void setMarketsCustomersList(List<MarketsCustomers> marketsCustomersList) {
        this.marketsCustomersList = marketsCustomersList;
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
