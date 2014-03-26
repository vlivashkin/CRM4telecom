package com.crm4telecom.enums;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {

    NEW("New", "#5bc0de") {
                @Override
                public OrderStatus nextStatus(OrderStep event) {
                    if (event == OrderStep.SEND_TO_TECH_SUPPORT
                    || event == OrderStep.ENGINEER_APPOINT) {
                        return OPENED;
                    }
                    if (event == OrderStep.CANCEL) {
                        return CLOSED;
                    }

                    return this;
                }

                @Override
                public List<OrderStep> possibleEvents() {
                    List<OrderStep> events = new ArrayList<>();
                    events.add(OrderStep.SEND_TO_TECH_SUPPORT);
                    events.add(OrderStep.ENGINEER_APPOINT);
                    events.add(OrderStep.CANCEL);
                    return events;
                }
            },
    OPENED("Opened", "#5cb85c") {
                @Override
                public OrderStatus nextStatus(OrderStep event) {
                    if (event == OrderStep.SUCCESS) {
                        return CLOSED;
                    }
                    if (event == OrderStep.CANCEL) {
                        return CLOSED;
                    }
                    return this;
                }

                @Override
                public List<OrderStep> possibleEvents() {
                    List<OrderStep> events = new ArrayList<>();
                    events.add(OrderStep.SUCCESS);
                    events.add(OrderStep.CANCEL);

                    return events;
                }
            },
    CLOSED("Closed", "#eee") {
                @Override
                public OrderStatus nextStatus(OrderStep event) {
                    return null;
                }

                @Override
                public List<OrderStep> possibleEvents() {
                    List<OrderStep> events = new ArrayList<>();

                    return events;
                }
            };

    private final String label;
    private final String color;

    private OrderStatus(String label, String color) {
        this.label = label;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public String getColorHex() {
        return color;
    }

    public abstract OrderStatus nextStatus(OrderStep event);

    public abstract List<OrderStep> possibleEvents();
}
