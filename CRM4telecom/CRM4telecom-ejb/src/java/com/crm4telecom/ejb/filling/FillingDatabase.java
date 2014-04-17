package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;

public abstract class FillingDatabase {

    public final void allocateItem(Customer customer) {
        getDataAndAlloc(customer);
    }

    public final void freeItem(Customer customer) {
        getDataAndFree(customer);
    }

    protected abstract void getDataAndAlloc(Customer customer);

    protected abstract void getDataAndFree(Customer customer);
}
