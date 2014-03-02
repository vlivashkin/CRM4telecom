package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderState;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author darya
 */
@ManagedBean
@RequestScoped
public class RequestBean {

    @EJB
    private CustomerManagerLocal cm;
    @EJB
    private OrderManagerLocal om;

    public List<String> getStatuses() {
        List<String> state = new ArrayList<>();
        OrderState[] orderStates = OrderState.values();
        for (OrderState orderState : orderStates) {
            state.add(orderState.toString());
        }
        return state;
    }

    public List<String> getPriority() {
        List<String> priority = new ArrayList<>();
        OrderPriority[] orderPriority = OrderPriority.values();
        for (OrderPriority orderPriority1 : orderPriority) {
            priority.add(orderPriority1.toString());
        }
        return priority;
    }

}
