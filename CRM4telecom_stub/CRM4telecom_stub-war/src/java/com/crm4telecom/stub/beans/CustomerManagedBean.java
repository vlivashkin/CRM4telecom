package com.crm4telecom.stub.beans;

import ejb.jpa.Customer;
import ejb.beans.CustomerManagerInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "cmb")
@ViewScoped
public class CustomerManagedBean {
    @EJB
    private CustomerManagerInterface customerManager;

    public List<Customer> getCustomers() {
        return customerManager.getCustomersList();
    }
           
    public void merge(Customer customer) {
        customerManager.merge(customer);
    }    
    
    public CustomerManagedBean() {
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
