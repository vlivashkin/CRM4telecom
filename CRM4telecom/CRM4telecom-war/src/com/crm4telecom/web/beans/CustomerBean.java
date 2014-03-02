package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.web.beans.util.LazyCustomerDataModel;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class CustomerBean implements Serializable {

    private LazyDataModel<Customer> lazyModel;
    private Customer customer;

    @EJB
    private CustomerManagerLocal cm;
    
    @Inject
    private CustomerValidationBean cv;
    
    @PostConstruct
    public void init() {
        lazyModel = new LazyCustomerDataModel(cm);
    }

    public LazyDataModel<Customer> getCustomers() {
        return lazyModel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        cv.init(customer);
    }

    public String onRowSelect() throws IOException {
        return "customer_info?includeViewParams=true";
    }
    
    public void save() {
        cv.fillCustomer(customer);
        cm.modifyCustomer(customer);
    }

    public CustomerValidationBean getCv() {
        return cv;
    }

    public void setCv(CustomerValidationBean cv) {
        this.cv = cv;
    }
}
