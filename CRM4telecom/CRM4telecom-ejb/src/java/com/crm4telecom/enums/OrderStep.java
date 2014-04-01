package com.crm4telecom.enums;

public enum OrderStep {

    PRE_CONFIRM("Pre-confirm", OrderStatus.OPENED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    if (flag) {
                        return SEND_TO_TECH_SUPPORT;
                    } else {
                        return ENGINEER_APPOINT;
                    }
                }
            },
    SEND_TO_TECH_SUPPORT("Send to technical support", OrderStatus.OPENED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return POST_CONFIRM;
                }
            },
    ENGINEER_APPOINT("Engineer appoint", OrderStatus.OPENED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return POST_CONFIRM;
                }
            },
    POST_CONFIRM("Post-confirm", OrderStatus.CLOSED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return POST_CONFIRM;
                }
            };

    private final String label;
    private final OrderStatus doneStatus;

    private OrderStep(String label, OrderStatus doneStatus) {
        this.label = label;
        this.doneStatus = doneStatus;
    }

    public String getLabel() {
        return this.label;
    }

    public OrderStatus getStatus() {
        return doneStatus;
    }

    public abstract OrderStep nextStep(Boolean flag);
}
