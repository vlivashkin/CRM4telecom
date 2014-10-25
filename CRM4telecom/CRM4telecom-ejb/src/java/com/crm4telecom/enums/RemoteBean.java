package com.crm4telecom.enums;

public enum RemoteBean {
    OrderManager("java:global/CRM4telecom/CRM4telecom-ejb/OrderManager!com.crm4telecom.ejb.OrderManagerRemote"),
    IpFilling("java:global/CRM4telecom/CRM4telecom-ejb/IpFilling!com.crm4telecom.ejb.filling.IpFillingRemote"),
    PhoneFilling("java:global/CRM4telecom/CRM4telecom-ejb/PhoneFilling!com.crm4telecom.ejb.filling.PhoneFillingRemote");
    
    private final String jndi;
    
    RemoteBean(String jndi) {
        this.jndi = jndi;
    }

    public String getJndi() {
        return jndi;
    }
}
