package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;

public abstract class FillingDatabase {

    public final Boolean allocateItem(Customer customer) {
        return getDataAndAlloc(customer);
    }

    public final Boolean activateItem(Customer customer) {
        return getDataAndActivate(customer);
    }

    public final Boolean freeItem(Customer customer) {
        return getDataAndFree(customer);
    }

    protected abstract Boolean getDataAndAlloc(Customer customer);

    protected abstract Boolean getDataAndActivate(Customer customer);

    protected abstract Boolean getDataAndFree(Customer customer);
}
