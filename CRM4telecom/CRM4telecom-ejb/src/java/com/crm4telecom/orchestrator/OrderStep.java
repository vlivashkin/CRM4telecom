package com.crm4telecom.orchestrator;

import com.crm4telecom.billing.BillingWebService;
import com.crm4telecom.billing.Services;
import com.crm4telecom.ejb.OrderManagerRemote;
import com.crm4telecom.ejb.filling.IpFillingRemote;
import com.crm4telecom.ejb.filling.PhoneFillingRemote;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.enums.ProductProperties;
import com.crm4telecom.jpa.CustomersProducts;
import com.crm4telecom.jpa.Order;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public enum OrderStep {

    PRE_CONFIRM(OrderStatus.OPENED, new UserTask("Pre-confirm")) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return SEND_TO_TECH_SUPPORT;

                }
            },
    SEND_TO_TECH_SUPPORT(OrderStatus.OPENED, new AutoTask("Send to technical support") {
        @Override
        public boolean run() {
            return true;
        }
    }) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return ALLOCATE_RESOURCE;
                }
            },
    ALLOCATE_RESOURCE(OrderStatus.OPENED, new AutoTask("Allocate resource") {

        @Override
        public boolean run() {
            Logger logger = Logger.getLogger(getClass().getName());
            Context ctx;
            try {
                ctx = new InitialContext();
                OrderManagerRemote om = (OrderManagerRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/OrderManager!com.crm4telecom.ejb.OrderManagerRemote");
                Long orderId = this.getOrderId();
                Order order = om.getOrder(orderId);
                IpFillingRemote ipFillingRemote = (IpFillingRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/IpFilling!com.crm4telecom.ejb.filling.IpFillingRemote");
                PhoneFillingRemote phoneFillingRemote = (PhoneFillingRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/PhoneFilling!com.crm4telecom.ejb.filling.PhoneFillingRemote");
                ProductProperties properties = order.getProduct().getProperties();
                if (properties.equals(ProductProperties.IP)) {
                    if (order.getOrderType().equals(OrderType.CONNECT)) {
                        return ipFillingRemote.allocateItem(order.getCustomer());
                    } else {
                        return ipFillingRemote.freeItem(order.getCustomer());
                    }
                } else if (properties.equals(ProductProperties.PHONE)) {
                    if (order.getOrderType().equals(OrderType.CONNECT)) {
                        return phoneFillingRemote.allocateItem(order.getCustomer());
                    } else {
                        return phoneFillingRemote.freeItem(order.getCustomer());
                    }
                } else if (properties.equals(ProductProperties.NONE)) {
                    return true;
                }
            } catch (Throwable ex) {
                logger.severe(ex.toString());
                return false;
            }
            return false;
        }
    }) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    if (flag) {
                        return BILLING;
                    } else {
                        return TECHNITIAN_APPOINT;
                    }
                }
            },
    TECHNITIAN_APPOINT(OrderStatus.OPENED, new UserTask("Technitian appoint")) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return BILLING;
                }
            },
    BILLING(OrderStatus.OPENED, new AutoTask("Billing") {
        @Override
        public boolean run() {
            Context ctx;
            OrderManagerRemote om = null;
            Services service = new Services();
            BillingWebService billingWebService = service.getBillingPort();
            IpFillingRemote ipFillingRemote = null;
            PhoneFillingRemote phoneFillingRemote = null;
            try {
                ctx = new InitialContext();
                om = (OrderManagerRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/OrderManager!com.crm4telecom.ejb.OrderManagerRemote");
                Long orderId = this.getOrderId();
                Order order = om.getOrder(orderId);
                ctx = new InitialContext();
                om = (OrderManagerRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/OrderManager!com.crm4telecom.ejb.OrderManagerRemote");
                ipFillingRemote = (IpFillingRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/IpFilling!com.crm4telecom.ejb.filling.IpFillingRemote");
                phoneFillingRemote = (PhoneFillingRemote) ctx.lookup("java:global/CRM4telecom/CRM4telecom-ejb/PhoneFilling!com.crm4telecom.ejb.filling.PhoneFillingRemote");
                ProductProperties properties = order.getProduct().getProperties();

                if (billingWebService.addProduct(order.getCustomerId(), order.getProduct().getName()) || billingWebService.withdraw(order.getProduct().getOnetimePayment(), order.getCustomerId())) {
                    if (properties.equals(ProductProperties.IP)) {
                        if (order.getOrderType().equals(OrderType.CONNECT)) {
                            ipFillingRemote.activateItem(order.getCustomer());
                        }

                    } else if (properties.equals(ProductProperties.PHONE)) {
                        if (order.getOrderType().equals(OrderType.CONNECT)) {
                            phoneFillingRemote.activateItem(order.getCustomer());
                        }
                    } else if (properties.equals(ProductProperties.NONE)) {
                    }
                    return true;
                }
            } catch (NamingException ex) {
                Logger.getLogger(OrderStep.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            return false;
        }
    }) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return POST_CONFIRM;
                }
            },
    POST_CONFIRM(OrderStatus.CLOSED, new UserTask("Post-confirm")) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    Context ctx;
                    OrderManagerRemote om = null;

                    return POST_CONFIRM;
                }
            };
    public OrderStatus doneStatus;
    public Task task;

    private OrderStep(OrderStatus doneStatus, Task task) {
        this.doneStatus = doneStatus;
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public String getLabel() {
        return task.getLabel();
    }

    public OrderStatus getStatus() {
        return doneStatus;
    }

    public abstract OrderStep nextStep(Boolean flag);
}
