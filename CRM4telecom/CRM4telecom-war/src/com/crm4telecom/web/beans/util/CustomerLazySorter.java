package com.crm4telecom.web.beans.util;

import com.crm4telecom.jpa.Customer;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class CustomerLazySorter implements Comparator<Customer> {
    private final String sortField;
    private final SortOrder sortOrder;
    
    public CustomerLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Customer customer1, Customer customer2) {
        try {
            Object value1 = Customer.class.getField(this.sortField).get(customer1);
            Object value2 = Customer.class.getField(this.sortField).get(customer2);

            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}