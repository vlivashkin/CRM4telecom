/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "EQUIPMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e"),
    @NamedQuery(name = "Equipment.findByEquipmentId", query = "SELECT e FROM Equipment e WHERE e.equipmentId = :equipmentId"),
    @NamedQuery(name = "Equipment.findByCustomerId", query = "SELECT e FROM Equipment e WHERE e.customerId = :customerId"),
    @NamedQuery(name = "Equipment.findByName", query = "SELECT e FROM Equipment e WHERE e.name = :name"),
    @NamedQuery(name = "Equipment.findBySerialNumber", query = "SELECT e FROM Equipment e WHERE e.serialNumber = :serialNumber"),
    @NamedQuery(name = "Equipment.findByDescription", query = "SELECT e FROM Equipment e WHERE e.description = :description"),
    @NamedQuery(name = "Equipment.findByStatus", query = "SELECT e FROM Equipment e WHERE e.status = :status")})
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EQUIPMENT_ID")
    private BigDecimal equipmentId;
    @Column(name = "CUSTOMER_ID")
    private BigInteger customerId;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Column(name = "SERIAL_NUMBER")
    private BigInteger serialNumber;
    @Size(max = 30)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 30)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "equipmentId")
    private Collection<OrderProcessing> orderProcessingCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "equipment")
    private EquipmentHistory equipmentHistory;
    @JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "EQUIPMENT_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EquipmentHistory equipmentHistory1;

    public Equipment() {
    }

    public Equipment(BigDecimal equipmentId) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<OrderProcessing> getOrderProcessingCollection() {
        return orderProcessingCollection;
    }

    public void setOrderProcessingCollection(Collection<OrderProcessing> orderProcessingCollection) {
        this.orderProcessingCollection = orderProcessingCollection;
    }

    public EquipmentHistory getEquipmentHistory() {
        return equipmentHistory;
    }

    public void setEquipmentHistory(EquipmentHistory equipmentHistory) {
        this.equipmentHistory = equipmentHistory;
    }

    public EquipmentHistory getEquipmentHistory1() {
        return equipmentHistory1;
    }

    public void setEquipmentHistory1(EquipmentHistory equipmentHistory1) {
        this.equipmentHistory1 = equipmentHistory1;
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
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        if ((this.equipmentId == null && other.equipmentId != null) || (this.equipmentId != null && !this.equipmentId.equals(other.equipmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.entities.Equipment[ equipmentId=" + equipmentId + " ]";
    }
    
}
