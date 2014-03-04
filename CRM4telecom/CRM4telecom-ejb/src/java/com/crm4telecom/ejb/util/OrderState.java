package com.crm4telecom.ejb.util;

public enum OrderState {

    NEW {
                @Override
                public OrderState nextState(OrderEvent event) {
                    if (event == OrderEvent.SENT_TO_TECH_SUPPORT || event == OrderEvent.ENGINEER_APPOINTED) {
                        return OPENED;
                    }
                    return this;
                }
            },
    OPENED {
                @Override
                public OrderState nextState(OrderEvent event) {
                    if (event == OrderEvent.DELAY) {
                        return WAITING;
                    } else if (event == OrderEvent.DONE) {
                        return CLOSED;
                    }
                    return this;
                }
            },
    WAITING {
                @Override
                public OrderState nextState(OrderEvent event) {
                    if (event == OrderEvent.READY) {
                        return OPENED;
                    } else if (event == OrderEvent.CANCELLED) {
                        return LOCKED;
                    }
                    return this;
                }
            },
    LOCKED {
                @Override
                public OrderState nextState(OrderEvent event) {
                    if (event == OrderEvent.READY) {
                        return OPENED;
                    }
                    return this;
                }
            },
    CLOSED {
                @Override
                public OrderState nextState(OrderEvent event) {
                    return this;
                }
            };

    public abstract OrderState nextState(OrderEvent event);
}
