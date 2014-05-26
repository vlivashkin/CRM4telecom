package com.crm4telecom.web.beans.util;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.OrderListBean;
import com.crm4telecom.web.util.StringUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyOrderDataModel extends LazyDataModel<Order> {

    private final OrderManagerLocal om;
    private List<Order> datasource;
    public OrderListBean search;
    public Map<String, List<String>> parameters;

    public void setSearch(OrderListBean search) {
        if (parameters == null) {
            parameters = new HashMap();
        }
        if (StringUtils.isValidString(search.getFromID())) {
            List<String> l = new ArrayList();
            l.add(search.getFromID());
            parameters.put("fromOrderId", l);
        } else {
            parameters.remove("fromOrderId");
        }
        if (StringUtils.isValidString(search.getToID())) {
            List<String> l = new ArrayList();
            l.add(search.getToID());
            parameters.put("toOrderId", l);
        } else {
            parameters.remove("toOrderId");
        }
        if (StringUtils.isValidString(search.getCustomer())) {
            List<String> l = new ArrayList();
            l.add(search.getCustomer());
            parameters.put("customer", l);
        } else {
            parameters.remove("customer");
        }
        if (StringUtils.isValidString(search.getEmployee())) {
            List<String> l = new ArrayList();
            l.add(search.getEmployee());
            parameters.put("employee", l);
        } else {
            parameters.remove("employee");
        }
        if (search.getFromDate() != null) {
            List<String> date = new ArrayList();
            Timestamp ts = new Timestamp(search.getFromDate().getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.getFromDate().toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date.add(d + "-" + m + "-" + y);
            parameters.put("fromDate", date);
        } else {
            parameters.remove("fromDate");
        }
        if (search.getToDate() != null) {
            List<String> date1 = new ArrayList();
            Timestamp ts = new Timestamp(search.getToDate().getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.getToDate().toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date1.add(d + "-" + m + "-" + y);
            parameters.put("toDate", date1);
        } else {
            parameters.remove("toDate");
        }
        if (search.getSelectedPriorities() != null && !search.getSelectedPriorities().isEmpty()) {
            parameters.put("priority", search.getSelectedPriorities());
        } else {
            parameters.remove("priority");
        }
        if (search.getSelectedStatuses() != null && !search.getSelectedStatuses().isEmpty()) {
            parameters.put("status", search.getSelectedStatuses());
        } else {
            parameters.remove("status");
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
    public List<Order> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(om.getOrdersCount(filters, parameters).intValue());
        datasource = om.getOrdersList(first, pageSize, sortField, sortOrder.name(), filters, parameters);
        parameters.clear();
        return datasource;
    }
}
