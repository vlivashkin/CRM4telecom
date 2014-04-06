package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PHONE_NUMBERS_HISTORY", catalog = "")
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
        if (!(object instanceof PhoneNumbersHistory)) {
            return false;
        }
        PhoneNumbersHistory other = (PhoneNumbersHistory) object;
        return this.phoneNumber.equals(other.phoneNumber);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.PhoneNumbersHistory[ phoneNumber=" + phoneNumber + " ]";
    }

}
