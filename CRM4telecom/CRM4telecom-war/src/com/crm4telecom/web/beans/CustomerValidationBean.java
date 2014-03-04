package com.crm4telecom.web.beans;

import com.crm4telecom.jpa.Customer;
import java.util.Date;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@ManagedBean
@RequestScoped
public class CustomerValidationBean {

    String firstName;

    String lastName;

    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",  message = "Invalid email address")
    String email;

    String street;

    Long building;

    Long flat;

    Long balance;

    @Pattern(regexp = "[\\d]{4} [\\d]{4} [\\d]{4} [\\d]{4}")
    String phoneNumber;

    @Past
    Date cardExpDate;

    public void init(Customer customer) {
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        email = customer.getEmail();
        street = customer.getStreet();
        building = customer.getBuilding();
        flat = customer.getFlat();
        balance = customer.getBalance();
        phoneNumber = customer.getphoneNumber();
    }

    public void fillCustomer(Customer customer) {
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setStreet(street);
        customer.setBuilding(building);
        customer.setFlat(flat);
        customer.setBalance(balance);
        customer.setphoneNumber(phoneNumber);
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

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
        this.building = building;
    }

    public Long getFlat() {
        return flat;
    }

    public void setFlat(Long flat) {
        this.flat = flat;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
