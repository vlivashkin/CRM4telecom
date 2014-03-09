package com.crm4telecom.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Embeddable
public class OrderProcessingPK implements Serializable {
    
    @NotNull
    @Column(name = "ORDER_ID")
    private Long orderId;
    
    @GeneratedValue(generator = "SEC_STEP_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_STEP_ID", sequenceName = "SEC_STEP_ID", allocationSize = 1)
    @Column(name = "STEP_ID")
    private Long stepId;

    public OrderProcessingPK() {
    }

    public OrderProcessingPK(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        hash += (stepId != null ? stepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderProcessingPK)) {
            return false;
        }
        OrderProcessingPK other = (OrderProcessingPK) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        if ((this.stepId == null && other.stepId != null) || (this.stepId != null && !this.stepId.equals(other.stepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.OrderProcessingPK[ orderId=" + orderId + ", stepId=" + stepId + " ]";
    }
    
}
