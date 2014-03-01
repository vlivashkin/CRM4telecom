package com.crm4telecom.web.beans;
 
import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.ejb.OrderManagerLocal;
import com.crm4telecom.ejb.util.OrderPriority;
import com.crm4telecom.ejb.util.OrderType;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.Orders;
import java.util.Date;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class ExampleBean{
        private static final long serialVersionUID = 1L;
    
        @EJB
        private CustomerManagerLocal cm;
        @EJB
        private OrderManagerLocal om;
        
	public void addCustomer() {
            cm.createCustomer("myname", "myfamily", "myemail", "mystreet", 1L, 1L, "mycardnumber", new Date(), new Long(3));
	}
        
        public void alterCustomer() {
            cm.modifyCustomer(1L, "changed", "myfamily", "myemail", "mystreet", 1L, 1L, "mycardnumber", new Date(), new Long(3));
	}
        
	public String getCustomer() {
            Customer customer = cm.getCustomer(5L); 
            if (customer != null)
                return customer.toString();
             else
                return "No information about customer #5";
	}
        
        public void setCustomer(String s) {
        }
        
        public String getCustomersList() {
            StringBuilder list = new StringBuilder();
            
            for (Customer temp : cm.getCustomerList())
                list.append(temp.getCustomerId()).append(" ").append(temp.getFirstName()).append('\n');
            return list.toString();
	}
        
        public void setCustomersList(String s) {
        }
        
        
        public void addOrder(){
            Orders current = om.createOrder(OrderType.TYPE1, "mytypecomment", null, OrderPriority.HIGH, null, true);
            om.setCustomer(current, 6l);
	}
        
        public void alterOrder(){
            om.modifyOrder(3L, OrderType.TYPE2, "mytypecomment", null, OrderPriority.NORMAL, null);
	}
        
	public String getOrder() {
                Orders order = om.getOrder(5L); 
                if (order != null)
                    return order.toString();
                else
                    return "No information about order #5";
	}
        
        public void setOrder(String s) {
        }
        
        public String getOrdersList() {
            StringBuilder list = new StringBuilder();
            
            for (Orders temp : om.getOrdersList())
                list.append(temp.getCustomerId()).append(" ").append(temp.getProductId()).append('\n');
            return list.toString();
	}
        
        public void setOrdersList(String s) {
        }
}