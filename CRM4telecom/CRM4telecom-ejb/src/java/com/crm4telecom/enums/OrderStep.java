package com.crm4telecom.enums;

public enum OrderStep {

    SEND_TO_TECH_SUPPORT("Send to technical support") {
                @Override
                public OrderStep nextStep() {
                    return IN_WORK;
                }
            },
    ENGINEER_APPOINT("Engineer appoint") {
                @Override
                public OrderStep nextStep() {
                    return IN_WORK;
                }
            },
    IN_WORK("In work") {
                @Override
                public OrderStep nextStep() {
                    return SUCCESS;
                }
            },
    SUCCESS("Close order") {
                @Override
                public OrderStep nextStep() {
                    return this;
                }
            },
    CANCEL("Cancel order") {
                @Override
                public OrderStep nextStep() {
                    return this;
                }
            };

    private final String label;

    private OrderStep(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public abstract OrderStep nextStep();
}
