/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Alex
 */
@ManagedBean
@SessionScoped
public class SearchBean implements Serializable {

    @EJB
    private CustomerManagerLocal cm;

    private Customer selectedCustomer;
    private List<Customer> customers;
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String building;
    private String flat;
    private String balance;
    private String cardNumber;
    private String status;
    private Date connectionDate;
    private String cardExpData;
    private Map<String, String> parametrs = new HashMap();

    public String getCardExpData() {

        return cardExpData;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    
    
    public CustomerManagerLocal getCm() {
        return cm;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getFlat() {
        return flat;
    }

    public String getBalance() {
        return balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCm(CustomerManagerLocal cm) {
        this.cm = cm;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCardExpData(String cardExpData) {
        this.cardExpData = cardExpData;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public String makeSearch() {
        if (firstName.length() != 0) {
            parametrs.put("firstName", firstName.toLowerCase());
        }
        if (lastName.length() != 0) {
            parametrs.put("lastName", lastName.toLowerCase());
        }
        if (email.length() != 0) {
            parametrs.put("email", email.toLowerCase());
        }
        if (street.length() != 0) {
            parametrs.put("street", street.toLowerCase());
        }
        if (building.length() != 0) {
            parametrs.put("building", building.toLowerCase());
        }
        if (flat.length() != 0) {
            parametrs.put("flat", flat.toLowerCase());
        }
        if(cardNumber.length() != 0){
            parametrs.put("cardNumber",cardNumber.toLowerCase());
        }
        if(cardExpData.length() != 0 ){
            parametrs.put("cardExpData", cardExpData.toLowerCase());
        }
        customers = cm.search(parametrs);
        return "search";
    }

}
