package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.web.beans.util.LazyCustomerDataModel;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@RequestScoped
public class CustomerListBean implements Serializable, ISearchBean<Customer> {

    @EJB
    private CustomerManagerLocal cm;

    private LazyCustomerDataModel lazyModel;
    private List<Customer> customers;
    private Customer selected;
    
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String building;
    private String flat;
    private String balance;
    private String phoneNumber;
    private String status;

    @Override
    @PostConstruct
    public void init() {
        lazyModel = new LazyCustomerDataModel(cm);
    }

    @Override
    public LazyDataModel<Customer> getLazyModel() {
        lazyModel.setSearch(this);
        return lazyModel;
    }

    @Override
    public Customer getSelected() {
        return selected;
    }

    @Override
    public void setSelected(Customer selected) {
        this.selected = selected;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void onRowSelect() {
        JSFHelper helper = new JSFHelper();
        helper.redirect("customer_info", "id", selected.getCustomerId().toString());
    }
}
