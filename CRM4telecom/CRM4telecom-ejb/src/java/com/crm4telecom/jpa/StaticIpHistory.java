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
@Table(name = "STATIC_IP_HISTORY", catalog = "", schema = "CRM4TELECOM")
public class StaticIpHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String ip;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Size(max = 30)
    @Column(name = "IP_COMMENT", length = 30)
    private String ipComment;

    @JoinColumn(name = "IP", referencedColumnName = "IP", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private StaticIp staticIp;

    public StaticIpHistory() {
    }

    public StaticIpHistory(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getIpComment() {
        return ipComment;
    }

    public void setIpComment(String ipComment) {
        this.ipComment = ipComment;
    }

    public StaticIp getStaticIp() {
        return staticIp;
    }

    public void setStaticIp(StaticIp staticIp) {
        this.staticIp = staticIp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ip != null ? ip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaticIpHistory)) {
            return false;
        }
        StaticIpHistory other = (StaticIpHistory) object;
        return this.ip.equals(other.ip);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.StaticIpHistory[ ip=" + ip + " ]";
    }

}
