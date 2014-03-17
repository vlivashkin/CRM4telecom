package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.util.StringUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ManagedBean
@SessionScoped
public class OrderValidationBean implements Serializable {

    @EJB
    private CustomerManagerLocal cm;

    @EJB
    private GetManagerLocal gm;

    private String customer;
    private String manager;
    private String employee;

    private Long customerId;
    private Long managerId;
    private Long employeeId;

    @Enumerated(EnumType.STRING)
    private OrderPriority priority;

    private String product;

    private Boolean technicalSupportFlag;
    
    private Boolean newOrder;

    public void init() {
        customerId = null;
        employeeId = null;
        customer = null;
        employee = null;
        priority = null;
        product = null;
        technicalSupportFlag = null;
        newOrder = true;
    }

    public void init(Order order) {
        if (order != null) {
            customerId = order.getCustomerId();
            employeeId = order.getEmployeeId();

            customer = StringUtils.toString(order.getCustomer());
            employee = StringUtils.toString(order.getEmployee());
            priority = order.getPriority();

            if (order.getProduct() != null) {
                product = order.getProduct().getName();
            }
            if (order.getTechnicalSupportFlag() != null) {
                technicalSupportFlag = Boolean.valueOf(order.getTechnicalSupportFlag());
            }
            newOrder = order.getOrderId() == null;
        } else {
            init();
        }
    }

    public void fillOrder(Order order) {
        order.setCustomer(cm.getCustomer(customerId));
        order.setEmployee(gm.getEmployee(employeeId));
        order.setProduct(gm.getProduct(product));
        order.setPriority(priority);
        order.setTechnicalSupportFlag(technicalSupportFlag.toString());
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        customerId = Long.parseLong(customer.substring(1, customer.indexOf(" ")));
        this.customer = customer;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        managerId = Long.parseLong(manager.substring(1, manager.indexOf(" ")));
        this.manager = manager;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        employeeId = Long.parseLong(employee.substring(1, employee.indexOf(" ")));
        this.employee = employee;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public Boolean getTechnicalSupportFlag() {
        return technicalSupportFlag;
    }

    public void setTechnicalSupportFlag(Boolean technicalSupportFlag) {
        this.technicalSupportFlag = technicalSupportFlag;
    }

    public List<String> completeCustomer(String query) {
        return gm.completeCustomer(query);
    }

    public List<String> completeEmployee(String query) {
        return gm.completeEmployee(query);
    }

    public Boolean isNewOrder() {
        return newOrder;
    }
}
