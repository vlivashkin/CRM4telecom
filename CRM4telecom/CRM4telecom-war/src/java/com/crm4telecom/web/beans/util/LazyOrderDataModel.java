package com.crm4telecom.web.beans.util;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.OrderSearchBean;
import com.crm4telecom.stringutils.StringUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyOrderDataModel extends LazyDataModel<Order> {

    private OrderManagerLocal om;
    private List<Order> datasource;
    public OrderSearchBean search;
    public Map<String, List<String>> parametrs;

    public void setSearch(OrderSearchBean search) {
        if (parametrs == null) {
            parametrs = new HashMap();
        }
        if (StringUtils.isValidString(search.getOrder())) {
            List<String> l = new ArrayList();
            l.add(search.getOrder());
            parametrs.put("orderId", l);
        } else {
            parametrs.remove("orderId");
        }
        if (StringUtils.isValidString(search.getCustomer())) {
            List<String> l = new ArrayList();
            l.add(search.getCustomer());
            parametrs.put("customerId", l);
        } else {
            parametrs.remove("customerId");
        }
        if (StringUtils.isValidString(search.getEmployee())) {
            List<String> l = new ArrayList();
            l.add(search.getEmployee());
            parametrs.put("employeeId", l);
        } else {
            parametrs.remove("employeeId");
        }
        if (search.getFromDate() != null) {
            List<String> date = new ArrayList();
            Timestamp ts = new Timestamp(search.getFromDate().getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.getFromDate().toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date.add(d + "-" + m + "-" + y);
            parametrs.put("fromDate", date);
        } else {
            parametrs.remove("fromDate");
        }
        if (search.getToDate() != null) {
            List<String> date1 = new ArrayList();
            Timestamp ts = new Timestamp(search.getToDate().getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.getToDate().toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date1.add(d + "-" + m + "-" + y);
            parametrs.put("toDate", date1);
        } else {
            parametrs.remove("toDate");
        }
        if (search.getSelectedPriorities() != null && !search.getSelectedPriorities().isEmpty()) {
            parametrs.put("priority", search.getSelectedPriorities());
        } else {
            parametrs.remove("priority");
        }
        if (search.getSelectedStatuses() != null && !search.getSelectedStatuses().isEmpty()) {
            parametrs.put("status", search.getSelectedStatuses());
        } else {
            parametrs.remove("status");
        }
        this.search = search;
    }

    public LazyOrderDataModel(OrderManagerLocal om) {
        this.om = om;
    }

    @Override
    public Order getRowData(String rowKey) {
        for (Order order : datasource) {
            Long orderId = order.getOrderId();
            if (orderId.toString().equals(rowKey)) {
                return order;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Order order) {
        return order.getOrderId().toString();
    }

    @Override
    public List<Order> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        setRowCount(om.getOrdersCount(filters, parametrs).intValue());
        System.out.println(parametrs);
        datasource = om.getOrdersList(first, pageSize, sortField, sortOrder.name(), filters, parametrs);
        parametrs.clear();
        return datasource;
    }
}
