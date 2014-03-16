package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.EmployeeManagerLocal;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderProduct;
import com.crm4telecom.jpa.Order;
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
    private EmployeeManagerLocal em;

    private String customer;
    private String manager;
    private String employee;

    @Enumerated(EnumType.STRING)
    private OrderPriority priority;

    @Enumerated(EnumType.STRING)
    private OrderProduct product;

    private Boolean technicalSupportFlag;

    public void init(Order order) {
        if (order != null) {
            if (order.getCustomerId() != null) {
                customer = order.getCustomerId().toString();
            }
            if (order.getPriority() != null) {
                priority = order.getPriority();
            }
            manager = order.getManagerId().toString();
            employee = order.getEmployeeId().toString();
            if (order.getProductId() != null) {
//                product = order.getProductId().getName();
            }
            if (order.getTechnicalSupportFlag() != null) {
                technicalSupportFlag = Boolean.valueOf(order.getTechnicalSupportFlag());
            }
        }
    }

    public void fillOrder(Order order) {
//        order.setCustomerId(customer == null ? null : cm.getCustomer(customer));
        order.setPriority(priority);
//        order.setManagerId(manager);
//        order.setEmployeeId(employee);
//        order.setProductId(pm.getProduct(pm.getProductId(product)));
        order.setTechnicalSupportFlag(technicalSupportFlag.toString());
    }

    public Long getCustomerId() {
//        return customer;
        return null;
    }

    public void setCustomerId(Long customerId) {
//        this.customerId = customerId;
    }

    public String getEmployee() {
//        return empl;
        return null;
    }

    public void setEmployee(String empl) {
//        employeeId = Long.valueOf(empl.substring(1, empl.indexOf(" ")));
//        this.empl = empl;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public OrderProduct getProduct() {
        return product;
    }

    public void setProduct(OrderProduct product) {
        this.product = product;
    }

    public Boolean getTechnicalSupportFlag() {
        return technicalSupportFlag;
    }

    public void setTechnicalSupportFlag(Boolean technicalSupportFlag) {
        this.technicalSupportFlag = technicalSupportFlag;
    }

    public List<String> complete(String query) {
        return em.completeEmployee(query);
    }

}
