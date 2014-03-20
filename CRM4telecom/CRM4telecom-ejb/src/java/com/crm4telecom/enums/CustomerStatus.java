package com.crm4telecom.enums;

import java.awt.Color;

public enum CustomerStatus {

    ACTIVE("Active", Color.GREEN),
    BLOCKED("Blocked", Color.CYAN),
    UNPLUGGED("Unplugged", Color.RED);

    private final String label;
    private final Color color;

    private CustomerStatus(String label, Color color) {
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
}
