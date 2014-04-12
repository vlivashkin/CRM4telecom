package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.enums.CustomerStatus;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Market;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.soapui.Billing;
import com.crm4telecom.soapui.Services;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class CustomerInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private CustomerManagerLocal cm;

    @Inject
    private CustomerValidationBean cv;

    private Long id;
    private Customer customer;
    private Order selectedOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        customer = cm.getCustomer(id);
        cv.init(customer);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        cv.init(customer);
    }

    public CustomerValidationBean getCv() {
        return cv;
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public void create() {
        Customer customerNew = new Customer();
        cv.fillCustomer(customerNew);
        cm.createCustomer(customerNew);

        customer = customerNew;

        JSFHelper helper = new JSFHelper();
        helper.redirect("customer_info", "id", customer.getCustomerId().toString());
    }

    public void modify() {
        cv.fillCustomer(customer);
        cm.modifyCustomer(customer);

        JSFHelper helper = new JSFHelper();
        helper.redirect("customer_info", "id", customer.getCustomerId().toString());
    }

    public void toSelectedOrder() {
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_info", "id", selectedOrder.getOrderId().toString());
    }

    public void toAddOrder() {
        JSFHelper helper = new JSFHelper();
        helper.redirect("order_add", "customer", customer.getCustomerId().toString());
    }

    public CustomerStatus[] getStatus() {
        return CustomerStatus.values();
    }

    public List<Market> getMarkets() {
        return cm.getMarkets(customer);
    }

    public void syncBalance() {
        Services service = new Services();
        Billing port = service.getBillingPort();
        Long result = port.getBalance(customer.getCustomerId());

        customer.setBalance(result);
        cm.modifyCustomer(customer);
    }

    public void updBalance() {
        Long id = customer.getCustomerId();
        customer = cm.getCustomer(id);
    }
}
