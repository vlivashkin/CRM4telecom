package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderEvent;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.jpa.Order;
import com.crm4telecom.web.beans.util.LazyOrderDataModel;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class OrderBean implements Serializable {

    private LazyOrderDataModel lazyModel;
    Order order;
    OrderEvent event;

    @EJB
    private OrderManagerLocal om;

    @Inject
    private OrderValidationBean validation;

    @Inject
    private OrderSearchBean search;
    
    @Inject
    private OrderCommentBean comment;
    
    private Map<String, List<String>> parametrs;

    @PostConstruct
    public void init() {
        lazyModel = new LazyOrderDataModel(om);
        parametrs = new HashMap();
    }

    public OrderValidationBean getValidation() {
        return validation;
    }

    public OrderSearchBean getSearch() {
        return search;
    }

    public OrderCommentBean getComment() {
        return comment;
    }
    
    public LazyDataModel<Order> getOrders() {
        
        if (search.order != null && search.order.length() != 0) {
            List<String> l = new ArrayList();
            System.out.println("aaaaaa");
            l.add(search.order);
            parametrs.put("orderId", l);
        }
        if (search.customer != null && search.customer.length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.customer);
            System.out.println("i am here bro");
            parametrs.put("customerId", l);
        }
        if (search.employee != null && search.employee.length() != 0) {
            List<String> l = new ArrayList();
            l.add(search.employee);
            parametrs.put("employeeId", l);
        }
        if (search.fromDate != null) {
            List<String> date = new ArrayList();
            Timestamp ts = new Timestamp(search.fromDate.getTime());
            System.out.println(ts.toString().substring(0, 10));
            String y = ts.toString().substring(0, 4);
            String m = search.fromDate.toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            System.out.println(d + " " + m + " " + y);
            
            date.add(d + "-" + m + "-" + y);
            parametrs.put("fromDate", date);
            
        }
        if (search.toDate != null) {
            List<String> date1 = new ArrayList();
            Timestamp ts = new Timestamp(search.toDate.getTime());
            String y = ts.toString().substring(0, 4);
            String m = search.toDate.toString().substring(4, 7).toUpperCase();
            String d = ts.toString().substring(8, 10);
            date1.add(d + "-" + m + "-" + y);
            parametrs.put("toDate", date1);
            
        }
        if (search.selectedPriorities != null && !search.selectedPriorities.isEmpty()) {
            parametrs.put("priority", search.selectedPriorities);
        }
        if (search.selectedStatuses != null && !search.selectedStatuses.isEmpty()) {
            parametrs.put("status", search.selectedStatuses);
        }
        if(search.toDate == null){
            parametrs.remove("toDate");
        }
        if(search.fromDate == null){
            parametrs.remove("fromDate");
        }
        if(search.order == null || search.order.length() == 0){
            parametrs.remove("orderId");
        }
//        System.out.println(search.toDate);
        System.out.println(parametrs);
        
        
        lazyModel.setParametrs(parametrs);
      //  parametrs.clear();
    ///  search.customer= null;
    //    search.selectedPriorities = null;
    //    search.selectedStatuses = null;
     //   search.employee = null;
     //   search.toDate = null;
     //   search.fromDate = null;
        return lazyModel;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        validation.init(order);
        comment.init(order);
    }

    public void onRowSelect() throws IOException {
        ConfigurableNavigationHandler configurableNavigationHandler
                = (ConfigurableNavigationHandler) FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation("order_info?faces-redirect=true");
    }

    public OrderPriority[] getPriorities() {
        return OrderPriority.values();
    }

    public OrderState[] getStatuses() {
        return OrderState.values();
    }

    public void create() {
        Order order = new Order();
        validation.fillOrder(order);
        om.createOrder(order);

        ConfigurableNavigationHandler configurableNavigationHandler
                = (ConfigurableNavigationHandler) FacesContext.
                getCurrentInstance().getApplication().getNavigationHandler();

        configurableNavigationHandler.performNavigation("order_list?faces-redirect=true");
    }

    public void modify() {
        validation.fillOrder(order);
        om.modifyOrder(order);
    }

    public List<OrderEvent> getEvents() {
        List<OrderEvent> events = new ArrayList<>();
        if (order != null) {
            String status = order.getStatus();
            if (status != null) {
                events = OrderState.valueOf(status).possibleEvents();
            }
        }
        return events;
    }

    public OrderEvent getEvent() {
        return event;
    }

    public void setEvent(OrderEvent event) {
        this.event = event;
    }

    public void changeState() {
        om.changeOrderState(order, event);
    }

    public List<String> completeOrder(String order) {
        return om.completeOrder(order);
    }
}
