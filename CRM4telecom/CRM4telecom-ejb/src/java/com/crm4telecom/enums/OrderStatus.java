package com.crm4telecom.enums;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {

    NEW("New", Color.BLUE) {
                @Override
                public OrderStatus nextState(OrderEvent event) {
                    if (event == OrderEvent.SEND_TO_TECH_SUPPORT
                    || event == OrderEvent.ENGINEER_APPOINT) {
                        return OPENED;
                    }
                    if (event == OrderEvent.CANCEL) {
                        return CLOSED;
                    }

                    return this;
                }

                @Override
                public List<OrderEvent> possibleEvents() {
                    List<OrderEvent> events = new ArrayList<>();
                    events.add(OrderEvent.SEND_TO_TECH_SUPPORT);
                    events.add(OrderEvent.ENGINEER_APPOINT);
                    events.add(OrderEvent.CANCEL);
                    return events;
                }
            },
    OPENED("Opened", Color.GREEN) {
                @Override
                public OrderStatus nextState(OrderEvent event) {
                    if (event == OrderEvent.SUCCESS) {
                        return CLOSED;
                    }
                    if (event == OrderEvent.CANCEL) {
                        return CLOSED;
                    }
                    return this;
                }

                @Override
                public List<OrderEvent> possibleEvents() {
                    List<OrderEvent> events = new ArrayList<>();
                    events.add(OrderEvent.SUCCESS);
                    events.add(OrderEvent.CANCEL);
                    
                    return events;
                }
            },
    CLOSED("Closed", Color.LIGHT_GRAY) {
                @Override
                public OrderStatus nextState(OrderEvent event) {
                    return null;
                }

                @Override
                public List<OrderEvent> possibleEvents() {
                    List<OrderEvent> events = new ArrayList<>();

                    return events;
                }
            };

    private final String label;
    private final Color color;

    private OrderStatus(String label, Color color) {
        this.label = label;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public Color getColor() {
        return color;
    }

    public String getColorHex() {
        return Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }

    public abstract OrderStatus nextState(OrderEvent event);

    public abstract List<OrderEvent> possibleEvents();
}
