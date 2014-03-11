package com.crm4telecom.web.beans.util;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.web.beans.CustomerSearchBean;
import com.crm4telecom.stringutils.StringUtils;
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
        if (StringUtils.isValidString(search.getLastName())) {
            List<String> l = new ArrayList();
            l.add(search.getLastName());
            parameters.put("lastName", l);
        } else {
            parameters.remove("lastName");
        }
        if (StringUtils.isValidString(search.getFirstName())) {
            List<String> l = new ArrayList();
            l.add(search.getFirstName());
            parameters.put("firstName", l);
        } else {
            parameters.remove("firstName");
        }
        if (StringUtils.isValidString(search.getEmail())) {
            List<String> l = new ArrayList();
            l.add(search.getEmail());
            parameters.put("email", l);
        } else {
            parameters.remove("email");
        }
        if (StringUtils.isValidString(search.getBalance())) {
            List<String> l = new ArrayList();
            l.add(search.getBalance());
            parameters.put("balance", l);
        } else {
            parameters.remove("balance");
        }
        if (StringUtils.isValidString(search.getStreet())) {
            List<String> l = new ArrayList();
            l.add(search.getStreet());
            parameters.put("street", l);
        } else {
            parameters.remove("street");
        }
        if (StringUtils.isValidString(search.getBuilding())) {
            List<String> l = new ArrayList();
            l.add(search.getBuilding());
            parameters.put("building", l);
        } else {
            parameters.remove("building");
        }
        if (StringUtils.isValidString(search.getFlat())) {
            List<String> l = new ArrayList();
            l.add(search.getFlat());
            parameters.put("flat", l);
        } else {
            parameters.remove("flat");
        }
        if (StringUtils.isValidString(search.getPhoneNumber())) {
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
