package com.crm4telecom.orchestrator;

import com.crm4telecom.billing.BillingWebService;
import com.crm4telecom.billing.Services;
import com.crm4telecom.ejb.OrderManagerRemote;
import com.crm4telecom.ejb.filling.IpFillingRemote;
import com.crm4telecom.ejb.filling.PhoneFillingRemote;
import com.crm4telecom.enums.OrderType;
import com.crm4telecom.enums.ProductProperties;
import com.crm4telecom.enums.RemoteBean;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.mail.MailManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Context ctx;
            try {
                ctx = new InitialContext();
                OrderManagerRemote om = (OrderManagerRemote) ctx.lookup(RemoteBean.OrderManager.getJndi());
                Long orderId = this.getOrderId();
                Order order = om.getOrder(orderId);

                ProductProperties properties = order.getProduct().getProperties();
                if (properties.equals(ProductProperties.IP)) {
                    IpFillingRemote ipFillingRemote = (IpFillingRemote) ctx.lookup(RemoteBean.IpFilling.getJndi());

                    if (order.getOrderType().equals(OrderType.CONNECT)) {
                        return ipFillingRemote.allocateItem(order.getCustomer());
                    } else {
                        return ipFillingRemote.freeItem(order.getCustomer());
                    }
                } else if (properties.equals(ProductProperties.PHONE)) {
                    PhoneFillingRemote phoneFillingRemote = (PhoneFillingRemote) ctx.lookup(RemoteBean.PhoneFilling.getJndi());

                    if (order.getOrderType().equals(OrderType.CONNECT)) {
                        return phoneFillingRemote.allocateItem(order.getCustomer());
                    } else {
                        return phoneFillingRemote.freeItem(order.getCustomer());
                    }
                } else {
                    return properties.equals(ProductProperties.NONE);
                }
            } catch (Throwable ex) {
                logger.warn(ex.toString());
                return false;
            }
        }
    }) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    if (flag) {
                        return BILLING;
                    } else {
                        return TECHNICIAN_APPOINT;
                    }
                }
            },
    TECHNICIAN_APPOINT(OrderStatus.OPENED, new UserTask("Technician appoint")) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return BILLING;
                }
            },
    BILLING(OrderStatus.OPENED, new AutoTask("Billing") {
        @Override
        public boolean run() {
            Context ctx;
            Services service = new Services();
            BillingWebService billingWebService = service.getBillingPort();

            try {
                ctx = new InitialContext();
                OrderManagerRemote om = (OrderManagerRemote) ctx.lookup(RemoteBean.OrderManager.getJndi());
                Long orderId = this.getOrderId();
                Order order = om.getOrder(orderId);
                ctx = new InitialContext();
                IpFillingRemote ipFillingRemote = (IpFillingRemote) ctx.lookup(RemoteBean.IpFilling.getJndi());
                PhoneFillingRemote phoneFillingRemote = (PhoneFillingRemote) ctx.lookup(RemoteBean.PhoneFilling.getJndi());
                ProductProperties properties = order.getProduct().getProperties();
                if (order.getOrderType().equals(OrderType.CONNECT)) {
                    logger.info("Here");
                    if (billingWebService.withdraw((order.getProduct().getOnetimePayment() + order.getInstallationFee()), order.getCustomerId()) && billingWebService.addProduct(order.getCustomerId(), order.getProduct().getProductId())) {
                        logger.info("Withdraw in main proj");
                        if (properties.equals(ProductProperties.IP)) {
                            ipFillingRemote.activateItem(order.getCustomer());
                        } else if (properties.equals(ProductProperties.PHONE)) {
                            phoneFillingRemote.activateItem(order.getCustomer());
                        } else if (properties.equals(ProductProperties.NONE)) {
                        }
                        return true;
                    }
                } else {
                    return true;
                }
            } catch (NamingException ex) {
                logger.warn("NamingException", ex);
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
                    return POST_CONFIRM;
                }
            };

    private static final Logger logger = LoggerFactory.getLogger(OrderStep.class);

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
