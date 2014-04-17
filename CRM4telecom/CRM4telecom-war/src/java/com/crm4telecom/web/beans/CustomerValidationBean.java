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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

@Named
@Dependent
public class CustomerValidationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerManagerLocal cm;

    @NotBlank(message = "You can't leave this empty.")
    @Size(max = 30, message = "This first name is too long.")
    @Pattern(regexp = "^[A-Za-z\\. ']*$", message = "You can use only characters")
    String firstName;

    @NotBlank(message = "You can't leave this empty.")
    @Size(max = 30, message = "This last name is too long.")
    @Pattern(regexp = "^[A-Za-z\\. ']*$", message = "You can use only characters")
    String lastName;

    @Size(max = 30, message = "This email is too long.")
    @Pattern(regexp = "^$|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Invalid email address")
    String email;

    String phoneNumber;

    @NotBlank(message = "You can't leave this empty.")
    @Size(max = 30, message = "This street name is too long.")
    @Pattern(regexp = "^[A-Za-z\\. ']*$", message = "You can use only characters")
    String street;

    List<String> markets;

    @NotNull(message = "You can't leave this empty.")
    Long building;

    Long flat;

    Long balance;

    @Enumerated(EnumType.STRING)
    CustomerStatus status;

    private Boolean newCustomer;

    public void init(Customer customer) {
        if (customer != null) {
            firstName = customer.getFirstName();
            lastName = customer.getLastName();
            email = customer.getEmail();
            street = customer.getStreet();
            building = customer.getBuilding();
            flat = customer.getFlat();
            balance = customer.getBalance();
            phoneNumber = customer.getPhoneNumber();
            status = customer.getStatus();

            markets = new ArrayList<String>();
            for (Market temp : cm.getMarkets(customer)) {
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
        customer.setBalance(balance);
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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
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
