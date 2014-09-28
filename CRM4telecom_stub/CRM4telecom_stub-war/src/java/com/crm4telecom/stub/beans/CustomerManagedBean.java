package com.crm4telecom.stub.beans;

import com.crm4telecom.stub.jpa.Customers;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CustomerManagedBean {
    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    public List<Customers> getCustomers() {
        return customerSessionBean.getCustomers();
    }
           
    public void merge(Customers customer) {
        customerSessionBean.merge(customer);
    }    
    
    public CustomerManagedBean() {
    }
    
}
