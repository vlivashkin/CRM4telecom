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
@Table(name = "STATIC_IP_HISTORY", catalog = "", schema = "CRM4TELECOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaticIpHistory.findAll", query = "SELECT s FROM StaticIpHistory s"),
    @NamedQuery(name = "StaticIpHistory.findByIp", query = "SELECT s FROM StaticIpHistory s WHERE s.ip = :ip"),
    @NamedQuery(name = "StaticIpHistory.findByCustomerId", query = "SELECT s FROM StaticIpHistory s WHERE s.customerId = :customerId"),
    @NamedQuery(name = "StaticIpHistory.findByStartDate", query = "SELECT s FROM StaticIpHistory s WHERE s.startDate = :startDate"),
    @NamedQuery(name = "StaticIpHistory.findByEndDate", query = "SELECT s FROM StaticIpHistory s WHERE s.endDate = :endDate"),
    @NamedQuery(name = "StaticIpHistory.findByIpComment", query = "SELECT s FROM StaticIpHistory s WHERE s.ipComment = :ipComment")})
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StaticIpHistory)) {
            return false;
        }
        StaticIpHistory other = (StaticIpHistory) object;
        if ((this.ip == null && other.ip != null) || (this.ip != null && !this.ip.equals(other.ip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.StaticIpHistory[ ip=" + ip + " ]";
    }
    
}
