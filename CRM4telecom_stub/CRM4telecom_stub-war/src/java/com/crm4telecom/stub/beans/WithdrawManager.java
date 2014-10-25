package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.ejb.beans.SchedulerUnitInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class WithdrawManager {

    @EJB
    private SchedulerUnitInterface schedulerUnit;

    public void forceWithdraw() {
        schedulerUnit.withdrawMoney(schedulerUnit.checkStatuses());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(WithdrawManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public WithdrawManager() {
    }

}
