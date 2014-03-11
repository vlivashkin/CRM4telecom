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

    private final CustomerManagerLocal cm;
    private List<Customer> datasource;
    public Map<String, List<String>> parameters;

    public LazyCustomerDataModel(CustomerManagerLocal cm) {
        this.cm = cm;
    }

    public void setSearch(CustomerSearchBean search) {
        if (parameters == null) {
            parameters = new HashMap();
        }
        if (search.getLastName() != null && search.getLastName().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getLastName());
            parameters.put("lastName", l);
        } else {
            parameters.remove("lastName");
        }
        if (search.getFirstName() != null && search.getFirstName().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getFirstName());
            parameters.put("firstName", l);
        } else {
            parameters.remove("firstName");
        }
        if (search.getEmail() != null && search.getEmail().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getEmail());
            parameters.put("email", l);
        } else {
            parameters.remove("email");
        }
        if (search.getBalance() != null && search.getBalance().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getBalance());
            parameters.put("balance", l);
        } else {
            parameters.remove("balance");
        }
        if (search.getStreet() != null && search.getStreet().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getStreet());
            parameters.put("street", l);
        } else {
            parameters.remove("street");
        }
        if (search.getBuilding() != null && search.getBuilding().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getBuilding());
            parameters.put("building", l);
        } else {
            parameters.remove("building");
        }
        if (search.getFlat() != null && search.getFlat().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getFlat());
            parameters.put("flat", l);
        } else {
            parameters.remove("flat");
        }
        if (search.getPhoneNumber() != null && search.getPhoneNumber().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getPhoneNumber());
            parameters.put("phoneNumber", l);
        } else {
            parameters.remove("phoneNumber");
        }
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
        setRowCount(cm.getCustomersCount(filters, parameters).intValue());
        datasource = cm.getCustomersList(first, pageSize, sortField, sortOrder.name(), filters, parameters);

        return datasource;
    }
}
