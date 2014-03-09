package com.crm4telecom.web.beans.util;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.OrderSearchBean;
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
    public Map<String,List<String>> parametrs;


    public void setSearch(OrderSearchBean search) {
         if(parametrs == null) parametrs = new HashMap();
        if (search.getOrder() != null && search.getOrder().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getOrder());
            parametrs.put("orderId", l);
        }
        if (search.getCustomer() != null && search.getCustomer().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getCustomer());
            parametrs.put("customerId", l);
        }
        if (search.getEmployee() != null && search.getEmployee().length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.getEmployee());
            parametrs.put("employeeId", l);
        }
        if (search.getFromDate() != null) {
            List<String> date = new ArrayList();
            Timestamp ts = new Timestamp(search.getFromDate().getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.getFromDate().toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date.add(d + "-" + m + "-" + y);
            parametrs.put("fromDate", date);
        }
        if (search.getToDate() != null) {
            List<String> date1 = new ArrayList();
            Timestamp ts = new Timestamp(search.getToDate().getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.getToDate().toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date1.add(d + "-" + m + "-" + y);
            parametrs.put("toDate", date1);
        }
        if (search.getSelectedPriorities() != null && !search.getSelectedPriorities().isEmpty()) {
            parametrs.put("priority", search.getSelectedPriorities());
        }
        if (search.getSelectedStatuses() != null && !search.getSelectedStatuses().isEmpty()) {
            parametrs.put("status", search.getSelectedStatuses());
        }
        if(search.getToDate() == null){
            parametrs.remove("toDate");
        }
        if(search.getFromDate() == null){
            parametrs.remove("fromDate");
        }
        if(search.getOrder() == null || search.getOrder().length() == 0){
            parametrs.remove("orderId");
        }
        if(search.getCustomer() == null || search.getCustomer().length() == 0 ){
            parametrs.remove("customerId");
        }
        if(search.getSelectedPriorities() == null || search.getSelectedPriorities().isEmpty()){
            parametrs.remove("priority");
        }
        if(search.getSelectedStatuses() == null || search.getSelectedStatuses().isEmpty()){
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
        setRowCount(om.getOrdersCount(filters,parametrs).intValue());
        System.out.println(parametrs);
        datasource = om.getOrdersList(first, pageSize, sortField, sortOrder.name(), filters,parametrs);
        parametrs.clear();

        return datasource;
    }
}
