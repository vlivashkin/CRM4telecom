package com.crm4telecom.jpa;

import com.crm4telecom.orchestrator.OrderStep;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "ORDER_PROCESSING", catalog = "")
public class OrderProcessing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEC_STEP_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_STEP_ID", sequenceName = "SEC_STEP_ID", allocationSize = 1)
    @Column(name = "STEP_ID", nullable = false, precision = 19, scale = 0)
    private Long stepId;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "END_DATE_HARD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateHard;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "STEP_NAME")
    @Enumerated(EnumType.STRING)
    private OrderStep stepName;

    @JoinColumn(name = "EQUIPMENT_ID", referencedColumnName = "EQUIPMENT_ID")
    @ManyToOne
    private Equipment equipmentId;

    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    @ManyToOne
    private Employee employeeId;

    public OrderProcessing() {
    }

    public OrderProcessing(Long stepId) {
        this.stepId = stepId;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getEndDateShort() {
        if (endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            return dateFormat.format(endDate);
        }
        return null;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateShort() {
        if (startDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            return dateFormat.format(startDate);
        }
        return null;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public OrderStep getStepName() {
        return stepName;
    }

    public void setStepName(OrderStep stepName) {
        this.stepName = stepName;
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
        hash += (stepId != null ? stepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
