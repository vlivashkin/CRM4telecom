package com.crm4telecom.web.beans.util;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.jpa.Orders;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyOrderDataModel extends LazyDataModel<Orders> {

    private OrderManagerLocal om;
    private List<Orders> datasource;

    public LazyOrderDataModel(OrderManagerLocal om) {
        this.om = om;
    }

    @Override
    public Orders getRowData(String rowKey) {
        for (Orders order : datasource) {
            Long orderId = order.getOrderId();
            if (orderId.toString().equals(rowKey)) {
                return order;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Orders order) {
        return order.getOrderId().toString();
    }

    @Override
    public List<Orders> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        setRowCount(om.getOrdersCount(filters).intValue());
        datasource = om.getOrdersList(first, pageSize, sortField, sortOrder.name(), filters);

        return datasource;
    }
}
