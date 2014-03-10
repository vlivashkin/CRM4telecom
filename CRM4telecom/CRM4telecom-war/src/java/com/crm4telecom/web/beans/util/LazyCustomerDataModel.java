package com.crm4telecom.web.beans.util;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.web.beans.CustomerSearchBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyCustomerDataModel extends LazyDataModel<Customer> {

    private CustomerManagerLocal cm;
    private List<Customer> datasource;
    private CustomerSearchBean search;
    public Map<String, List<String>> parametrs;

    public LazyCustomerDataModel(CustomerManagerLocal cm) {
        this.cm = cm;
    }

    public void setSearch(CustomerSearchBean search) {
        if (parametrs == null) {
            parametrs = new HashMap();
        }
        if (search.getLastName() != null && search.getLastName().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getLastName());
            parametrs.put("lastName", l);
        } else {
            parametrs.remove("lastName");
        }
        if (search.getFirstName() != null && search.getFirstName().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getFirstName());
            parametrs.put("firstName", l);
        } else {
            parametrs.remove("firstName");
        }
        if (search.getEmail() != null && search.getEmail().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getEmail());
            parametrs.put("email", l);
        } else {
            parametrs.remove("email");
        }
        if (search.getBalance() != null && search.getBalance().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getBalance());
            parametrs.put("balance", l);
        } else {
            parametrs.remove("balance");
        }
        if (search.getStreet() != null && search.getStreet().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getStreet());
            parametrs.put("street", l);
        } else {
            parametrs.remove("street");
        }
        if (search.getBuilding() != null && search.getBuilding().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getBuilding());
            parametrs.put("building", l);
        } else {
            parametrs.remove("building");
        }
        if (search.getFlat() != null && search.getFlat().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getFlat());
            parametrs.put("flat", l);
        } else {
            parametrs.remove("flat");
        }
        if (search.getPhoneNumber() != null && search.getPhoneNumber().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getPhoneNumber());
            parametrs.put("phoneNumber", l);
        } else {
            parametrs.remove("phoneNumber");
        }

        this.search = search;
    }

    @Override
    public Customer getRowData(String rowKey) {
        for (Customer customer : datasource) {
            Long customerId = customer.getCustomerId();
            if (customerId.toString().equals(rowKey)) {
                return customer;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Customer customer) {
        return customer.getCustomerId().toString();
    }

    @Override
    public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        setRowCount(cm.getCustomersCount(filters, parametrs).intValue());
        datasource = cm.getCustomersList(first, pageSize, sortField, sortOrder.name(), filters, parametrs);

        return datasource;
    }
}
