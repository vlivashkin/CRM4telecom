/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex
 */
@Embeddable
public class OrderProcessingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDER_ID")
    private BigInteger orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STEP_ID")
    private BigInteger stepId;

    public OrderProcessingPK() {
    }

    public OrderProcessingPK(BigInteger orderId, BigInteger stepId) {
        this.orderId = orderId;
        this.stepId = stepId;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public BigInteger getStepId() {
        return stepId;
    }

    public void setStepId(BigInteger stepId) {
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
