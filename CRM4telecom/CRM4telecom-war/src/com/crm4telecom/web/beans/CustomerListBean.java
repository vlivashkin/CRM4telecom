package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.web.beans.util.LazyCustomerDataModel;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class CustomerListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private LazyDataModel<Customer> lazyModel;
    private Customer selectedCustomer;

    @EJB
    private CustomerManagerLocal cm;
    
    @Inject
    private NavigationBean nb;
    
    @PostConstruct
    public void init() {
        lazyModel = new LazyCustomerDataModel(cm);
    }

    public LazyDataModel<Customer> getCustomers() {
        return lazyModel;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    
    public String toCustomerInfo() {
        return "customer_info?includeViewParams=true";
    }
}
