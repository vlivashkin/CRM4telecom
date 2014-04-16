package com.crm4telecom.jpa;

import com.crm4telecom.enums.IpStatus;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "STATIC_IP", catalog = "")
public class StaticIp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String ip;

    @Enumerated(EnumType.STRING)
    private IpStatus status;

    @Size(max = 30)
    @Column(name = "STATUS_COMMENT", length = 30)
    private String statusComment;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "staticIp")
    private StaticIpHistory staticIpHistory;

    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @ManyToOne
    private Customer customerId;

    public StaticIp() {
    }

    public StaticIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IpStatus getStatus() {
        return status;
    }

    public void setStatus(IpStatus status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public StaticIpHistory getStaticIpHistory() {
        return staticIpHistory;
    }

    public void setStaticIpHistory(StaticIpHistory staticIpHistory) {
        this.staticIpHistory = staticIpHistory;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ip != null ? ip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StaticIp)) {
            return false;
        }
        StaticIp other = (StaticIp) object;
        return this.ip.equals(other.ip);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.StaticIp[ ip=" + ip + " ]";
    }

}
