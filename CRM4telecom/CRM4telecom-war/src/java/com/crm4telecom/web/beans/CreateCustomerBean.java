
package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author darya
 */

@ManagedBean
@RequestScoped
public class CreateCustomerBean{
    private Long customerId;
    private String lastName;
    private String firstName;
    private String email;
    private String street;
    private Long building;
    private Long flat;
    private String phoneNumber;
    private String status;
    
    private Customer selectedCustomer;
    
    @EJB
    private CustomerManagerLocal cm;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    
    public String create(){
        selectedCustomer.setCustomerId(customerId);
        selectedCustomer.setLastName(lastName);
        selectedCustomer.setFirstName(firstName);
        selectedCustomer.setEmail(email);
        selectedCustomer.setStatus(status);
        selectedCustomer.setPhoneNumber(phoneNumber);
        selectedCustomer.setFlat(flat);
        selectedCustomer.setStreet(street);
        selectedCustomer.setBuilding(building);
        
        cm.createCustomer(selectedCustomer);
        return "index?faces-redirect=true";
    }
    
}
