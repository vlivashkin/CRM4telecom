package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(catalog = "")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "EQUIPMENT_ID", nullable = false, precision = 38, scale = 0)
    private Long equipmentId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Size(max = 30)
    @Column(length = 30)
    private String name;

    @Column(name = "SERIAL_NUMBER")
    private Long serialNumber;

    @Size(max = 30)
    @Column(length = 30)
    private String description;

    @Size(max = 30)
    @Column(length = 30)
    private String status;

    @OneToMany(mappedBy = "equipmentId")
    private List<OrderProcessing> orderProcessingList;

    public Equipment() {
    }

    public Equipment(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
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
    public List<OrderProcessing> getOrderProcessingList() {
        return orderProcessingList;
    }

    public void setOrderProcessingList(List<OrderProcessing> orderProcessingList) {
        this.orderProcessingList = orderProcessingList;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipmentId != null ? equipmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        return this.equipmentId.equals(other.equipmentId);
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.Equipment[ equipmentId=" + equipmentId + " ]";
    }

}
