package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.enums.CustomerStatus;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Market;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Named
@Dependent
public class CustomerValidationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 30, message = "This first name is too long.")
    @Pattern(regexp = "^[A-Za-z\\. ']*$", message = "You can use only characters")
    private String firstName;

    @Size(max = 30, message = "This last name is too long.")
    @Pattern(regexp = "^[A-Za-z\\. ']*$", message = "You can use only characters")
    private String lastName;

    @Size(max = 30, message = "This email is too long.")
    @Pattern(regexp = "^$|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Invalid email address")
    private String email;

    private String phoneNumber;

    @Size(max = 30, message = "This street name is too long.")
    @Pattern(regexp = "^[A-Za-z\\. ']*$", message = "You can use only characters")
    private String street;

    private List<String> markets;

    private Long building;

    private Long flat;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private Boolean newCustomer;

    public void init(Customer customer) {
        if (customer != null) {
            firstName = customer.getFirstName();
            lastName = customer.getLastName();
            email = customer.getEmail();
            street = customer.getStreet();
            building = customer.getBuilding();
            flat = customer.getFlat();
            phoneNumber = customer.getPhoneNumber();
            status = customer.getStatus();

            markets = new ArrayList<>();
            for (Market temp : customer.getMarkets()) {
                markets.add(temp.getName());
            }

            newCustomer = customer.getCustomerId() == null;
        }
    }

    public void fillCustomer(Customer customer) {
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setStreet(street);
        customer.setBuilding(building);
        customer.setFlat(flat);
        customer.setPhoneNumber(phoneNumber);
        customer.setStatus(status);
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public List<String> getMarkets() {
        return markets;
    }

    public void setMarkets(List<String> markets) {
        this.markets = markets;
    }

    public Boolean isNewCustomer() {
        if (newCustomer == null) {
            return true;
        }
        return newCustomer;
    }
}
