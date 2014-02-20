/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ORDER_PROCESSING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderProcessing.findAll", query = "SELECT o FROM OrderProcessing o"),
    @NamedQuery(name = "OrderProcessing.findByOrderId", query = "SELECT o FROM OrderProcessing o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderProcessing.findByStepName", query = "SELECT o FROM OrderProcessing o WHERE o.stepName = :stepName"),
    @NamedQuery(name = "OrderProcessing.findByDescription", query = "SELECT o FROM OrderProcessing o WHERE o.description = :description"),
    @NamedQuery(name = "OrderProcessing.findByStartDate", query = "SELECT o FROM OrderProcessing o WHERE o.startDate = :startDate"),
    @NamedQuery(name = "OrderProcessing.findByEndDate", query = "SELECT o FROM OrderProcessing o WHERE o.endDate = :endDate"),
    @NamedQuery(name = "OrderProcessing.findByEndDateHard", query = "SELECT o FROM OrderProcessing o WHERE o.endDateHard = :endDateHard")})
public class OrderProcessing implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDER_ID")
    private BigDecimal orderId;
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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderProcessing")
    private Orders orders;
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Orders orders1;
    @JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "EQUIPMENT_ID")
    @ManyToOne
    private Equipment equipmentId;
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne
    private Employee employeeId;

    public OrderProcessing() {
    }

    public OrderProcessing(BigDecimal orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderId() {
        return orderId;
    }

    public void setOrderId(BigDecimal orderId) {
        this.orderId = orderId;
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

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Orders getOrders1() {
        return orders1;
    }

    public void setOrders1(Orders orders1) {
        this.orders1 = orders1;
    }

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderProcessing)) {
            return false;
        }
        OrderProcessing other = (OrderProcessing) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.entities.OrderProcessing[ orderId=" + orderId + " ]";
    }
    
}
