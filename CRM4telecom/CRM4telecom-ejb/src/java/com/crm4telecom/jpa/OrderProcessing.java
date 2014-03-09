/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "ORDER_PROCESSING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderProcessing.findAll", query = "SELECT o FROM OrderProcessing o"),
    @NamedQuery(name = "OrderProcessing.findByOrderId", query = "SELECT o FROM OrderProcessing o WHERE o.orderProcessingPK.orderId = :orderId"),
    @NamedQuery(name = "OrderProcessing.findByStepId", query = "SELECT o FROM OrderProcessing o WHERE o.orderProcessingPK.stepId = :stepId"),
    @NamedQuery(name = "OrderProcessing.findByStepName", query = "SELECT o FROM OrderProcessing o WHERE o.stepName = :stepName"),
    @NamedQuery(name = "OrderProcessing.findByDescription", query = "SELECT o FROM OrderProcessing o WHERE o.description = :description"),
    @NamedQuery(name = "OrderProcessing.findByStartDate", query = "SELECT o FROM OrderProcessing o WHERE o.startDate = :startDate"),
    @NamedQuery(name = "OrderProcessing.findByEndDate", query = "SELECT o FROM OrderProcessing o WHERE o.endDate = :endDate"),
    @NamedQuery(name = "OrderProcessing.findByEndDateHard", query = "SELECT o FROM OrderProcessing o WHERE o.endDateHard = :endDateHard"),
    @NamedQuery(name = "OrderProcessing.findByEmployeeId", query = "SELECT o FROM OrderProcessing o WHERE o.employeeId = :employeeId"),
    @NamedQuery(name = "OrderProcessing.findByEquipmentId", query = "SELECT o FROM OrderProcessing o WHERE o.equipmentId = :equipmentId")})
public class OrderProcessing implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderProcessingPK orderProcessingPK;
    @Size(max = 30)
    @Column(name = "STEP_NAME")
    private String stepName;
    @Size(max = 30)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "END_DATE_HARD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateHard;
    @Column(name = "EMPLOYEE_ID")
    private BigInteger employeeId;
    @Column(name = "EQUIPMENT_ID")
    private BigInteger equipmentId;

    public OrderProcessing() {
    }

    public OrderProcessing(OrderProcessingPK orderProcessingPK) {
        this.orderProcessingPK = orderProcessingPK;
    }

    public OrderProcessing(BigInteger orderId, BigInteger stepId) {
        this.orderProcessingPK = new OrderProcessingPK(orderId, stepId);
    }

    public OrderProcessingPK getOrderProcessingPK() {
        return orderProcessingPK;
    }

    public void setOrderProcessingPK(OrderProcessingPK orderProcessingPK) {
        this.orderProcessingPK = orderProcessingPK;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getEndDateHard() {
        return endDateHard;
    }

    public void setEndDateHard(Date endDateHard) {
        this.endDateHard = endDateHard;
    }

    public BigInteger getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(BigInteger employeeId) {
        this.employeeId = employeeId;
    }

    public BigInteger getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(BigInteger equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderProcessingPK != null ? orderProcessingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderProcessing)) {
            return false;
        }
        OrderProcessing other = (OrderProcessing) object;
        if ((this.orderProcessingPK == null && other.orderProcessingPK != null) || (this.orderProcessingPK != null && !this.orderProcessingPK.equals(other.orderProcessingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.OrderProcessing[ orderProcessingPK=" + orderProcessingPK + " ]";
    }
    
}
