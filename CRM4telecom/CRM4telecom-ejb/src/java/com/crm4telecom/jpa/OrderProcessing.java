package com.crm4telecom.jpa;

import com.crm4telecom.enums.OrderStep;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Order_Processing", catalog = "", schema = "CRM4TELECOM")
public class OrderProcessing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEC_STEP_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_STEP_ID", sequenceName = "SEC_STEP_ID", allocationSize = 1)
    @Column(name = "STEP_ID")
    private Long stepId;

    @Column(name = "STEP_NAME")
    @Enumerated(EnumType.STRING)
    private OrderStep stepEvent;

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

    @JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "EQUIPMENT_ID")
    @ManyToOne
    private Equipment equipmentId;

    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne
    private Employee employeeId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    public OrderProcessing() {
    }

    public Long getStepId() {
        return stepId;
    }

    public OrderStep getStepEvent() {
        return stepEvent;
    }

    public void setStepEvent(OrderStep stepEvent) {
        this.stepEvent = stepEvent;
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

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long order) {
        this.orderId = order;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stepId != null ? stepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderProcessing)) {
            return false;
        }
        OrderProcessing other = (OrderProcessing) object;
        return (this.stepId != null || other.stepId == null) && (this.stepId == null || this.stepId.equals(other.stepId));
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.OrderProcessing[ stepId=" + stepId + " ]";
    }

}
