package com.crm4telecom.ejb.filling;

import com.crm4telecom.jpa.Customer;

public abstract class FillingDatabase {

    public final void fillData(Customer customer) {
        getDataAndFill(customer);
    }

    protected abstract void getDataAndFill(Customer customer);

}
