package com.crm4telecom.enums;

public enum OrderStep {

    PRE_CONFIRM("Pre-confirm", OrderStatus.NEW) {
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
    POST_CONFIRM("Post-confirm", OrderStatus.OPENED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return DONE;
                }
            },
    DONE("Done", OrderStatus.CLOSED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return this;
                }
            },
    CANCEL("Cancel order", OrderStatus.CLOSED) {
                @Override
                public OrderStep nextStep(Boolean flag) {
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

    public abstract OrderStep nextStep(Boolean flag);
}
