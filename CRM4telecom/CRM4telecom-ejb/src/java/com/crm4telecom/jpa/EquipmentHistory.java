/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "EQUIPMENT_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipmentHistory.findAll", query = "SELECT e FROM EquipmentHistory e"),
    @NamedQuery(name = "EquipmentHistory.findByEquipmentId", query = "SELECT e FROM EquipmentHistory e WHERE e.equipmentId = :equipmentId"),
    @NamedQuery(name = "EquipmentHistory.findByCustomerId", query = "SELECT e FROM EquipmentHistory e WHERE e.customerId = :customerId"),
    @NamedQuery(name = "EquipmentHistory.findByStartDate", query = "SELECT e FROM EquipmentHistory e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "EquipmentHistory.findByEndDate", query = "SELECT e FROM EquipmentHistory e WHERE e.endDate = :endDate"),
    @NamedQuery(name = "EquipmentHistory.findByEquipmentComment", query = "SELECT e FROM EquipmentHistory e WHERE e.equipmentComment = :equipmentComment"),
    @NamedQuery(name = "EquipmentHistory.findByStatus", query = "SELECT e FROM EquipmentHistory e WHERE e.status = :status")})
public class EquipmentHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EQUIPMENT_ID")
    private BigDecimal equipmentId;
    @Column(name = "CUSTOMER_ID")
    private BigInteger customerId;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Size(max = 30)
    @Column(name = "EQUIPMENT_COMMENT")
    private String equipmentComment;
    @Size(max = 30)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "EQUIPMENT_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Equipment equipment;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "equipmentHistory1")
    private Equipment equipment1;

    public EquipmentHistory() {
    }

    public EquipmentHistory(BigDecimal equipmentId) {
        this.equipmentId = equipmentId;
    }

    public BigDecimal getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(BigDecimal equipmentId) {
        this.equipmentId = equipmentId;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customerId) {
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

    public String getEquipmentComment() {
        return equipmentComment;
    }

    public void setEquipmentComment(String equipmentComment) {
        this.equipmentComment = equipmentComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment1() {
        return equipment1;
    }

    public void setEquipment1(Equipment equipment1) {
        this.equipment1 = equipment1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipmentId != null ? equipmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquipmentHistory)) {
            return false;
        }
        EquipmentHistory other = (EquipmentHistory) object;
        if ((this.equipmentId == null && other.equipmentId != null) || (this.equipmentId != null && !this.equipmentId.equals(other.equipmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.entities.EquipmentHistory[ equipmentId=" + equipmentId + " ]";
    }
    
}
