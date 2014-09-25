package com.crm4telecom.orchestrator;

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
                    if (flag) {
                        return ALLOCATE_RESOURCE;
                    } else {
                        return TECHNITIAN_APPOINT;
                    }
                }
            },
    ALLOCATE_RESOURCE(OrderStatus.OPENED, new AutoTask("Allocate resource") {
        @Override
        public boolean run() {
            return true;
        }
    }) {
                @Override
                public OrderStep nextStep(Boolean flag) {
                    return BILLING;
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
            return true;
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
    OrderStatus doneStatus;
    Task task;

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
