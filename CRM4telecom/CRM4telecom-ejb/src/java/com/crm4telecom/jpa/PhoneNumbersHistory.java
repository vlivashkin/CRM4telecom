package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PHONE_NUMBERS_HISTORY", catalog = "", schema = "CRM4TELECOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PhoneNumbersHistory.findAll", query = "SELECT p FROM PhoneNumbersHistory p"),
    @NamedQuery(name = "PhoneNumbersHistory.findByPhoneNumber", query = "SELECT p FROM PhoneNumbersHistory p WHERE p.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "PhoneNumbersHistory.findByCustomerId", query = "SELECT p FROM PhoneNumbersHistory p WHERE p.customerId = :customerId"),
    @NamedQuery(name = "PhoneNumbersHistory.findByStartDate", query = "SELECT p FROM PhoneNumbersHistory p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "PhoneNumbersHistory.findByEndDate", query = "SELECT p FROM PhoneNumbersHistory p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "PhoneNumbersHistory.findByHistoryComment", query = "SELECT p FROM PhoneNumbersHistory p WHERE p.historyComment = :historyComment")})
public class PhoneNumbersHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PHONE_NUMBER", nullable = false, length = 20)
    private String phoneNumber;
    
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Size(max = 30)
    @Column(name = "HISTORY_COMMENT", length = 30)
    private String historyComment;
    
    @JoinColumn(name = "PHONE_NUMBER", referencedColumnName = "PHONE_NUMBER", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PhoneNumber phoneNumber1;

    public PhoneNumbersHistory() {
    }

    public PhoneNumbersHistory(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHistoryComment() {
        return historyComment;
    }

    public void setHistoryComment(String historyComment) {
        this.historyComment = historyComment;
    }

    public PhoneNumber getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(PhoneNumber phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhoneNumbersHistory)) {
            return false;
        }
        PhoneNumbersHistory other = (PhoneNumbersHistory) object;
        if ((this.phoneNumber == null && other.phoneNumber != null) || (this.phoneNumber != null && !this.phoneNumber.equals(other.phoneNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.PhoneNumbersHistory[ phoneNumber=" + phoneNumber + " ]";
    }
    
}
