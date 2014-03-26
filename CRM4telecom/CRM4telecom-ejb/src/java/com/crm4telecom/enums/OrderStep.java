package com.crm4telecom.enums;

public enum OrderStep {

    SEND_TO_TECH_SUPPORT("Send to technical support", OrderStatus.NEW) {
                @Override
                public OrderStep nextStep() {
                    return IN_WORK;
                }
            },
    ENGINEER_APPOINT("Engineer appoint", OrderStatus.NEW) {
                @Override
                public OrderStep nextStep() {
                    return IN_WORK;
                }
            },
    IN_WORK("In work", OrderStatus.OPENED) {
                @Override
                public OrderStep nextStep() {
                    return SUCCESS;
                }
            },
    SUCCESS("Close order", OrderStatus.CLOSED) {
                @Override
                public OrderStep nextStep() {
                    return this;
                }
            },
    CANCEL("Cancel order", OrderStatus.CLOSED) {
                @Override
                public OrderStep nextStep() {
                    return this;
                }
            };

    private final String label;
    private final OrderStatus status;
    
    private OrderStep(String label, OrderStatus status) {
        this.label = label;
        this.status = status;
    }

    public String getLabel() {
        return this.label;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public abstract OrderStep nextStep();
}
