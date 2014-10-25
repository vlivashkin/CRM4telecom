package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.enums.OrderPriority;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.util.StringUtils;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Named
@Dependent
public class OrderValidationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerManagerLocal cm;

    @EJB
    private GetManagerLocal gm;

    private String customer;
    private String employee;
    private String product;

    private Long customerId;
    private Long employeeId;
    private Long productId;

    @Enumerated(EnumType.STRING)
    private OrderType type = OrderType.CONNECT;

    @Enumerated(EnumType.STRING)
    private OrderPriority priority;

    private Boolean technicalSupportFlag;

    private Boolean newOrder;

    public void setProductId(Long productId) {
        this.productId = productId;
        product = gm.getProduct(productId).toString();
    }
    
    public Long getProductId() {
        return productId;
    }

    public GetManagerLocal getGm() {
        return gm;
    }

    public void init(Order order) {
        if (order != null) {
            customerId = order.getCustomerId();
            employeeId = order.getEmployeeId();

            customer = StringUtils.toString(order.getCustomer());
            employee = StringUtils.toString(order.getEmployee());
            priority = order.getPriority();
            type = order.getOrderType();
            if (order.getProduct() != null) {
                product = order.getProduct().getName();
            }
            if (order.getTechSupport() != null) {
                technicalSupportFlag = order.getTechSupport();
            }
            newOrder = order.getOrderId() == null;
        }
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        if (customerId != null) {
            this.customerId = customerId;
            customer = StringUtils.toString(cm.getCustomer(customerId));
        }
    }

    public void fillOrder(Order order) {
        order.setCustomer(cm.getCustomer(customerId));
        order.setEmployee(gm.getEmployee(employeeId));
        order.setProduct(gm.getProduct(productId));
        order.setPriority(priority);
        if (isNewOrder()) {
            order.setOrderType(type);
            order.setTechSupport(technicalSupportFlag);
        }
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        customerId = Long.parseLong(customer.substring(1, customer.indexOf(" ")));
        this.customer = customer;
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
        if (product == null || "Name".equals(product)) {
            productId = null;
            this.product = null;
        } else {
            productId = Long.parseLong(product.substring(1, product.indexOf(" ")));
            this.product = product;
        }
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = OrderType.valueOf(type);
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
        if (newOrder == null) {
            return true;
        }
        return newOrder;
    }
}
