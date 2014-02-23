package com.crm4telecom.web.beans;
 
import com.crm4telecom.ejb.CustomerManagerLocal;
import com.crm4telecom.jpa.Customer;
import java.util.Date;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class UserBean{
    
        private static final long serialVersionUID = 1L;
    
        @Inject
        private CustomerManagerLocal cm;
        
	public void add(){
            cm.addCustomer("rr", "rrr", "kkk", "ede", new Date(), new Long(3));
	}
        
        public void alter(){
            cm.alterCustomer(new Long(1), "changed", "rrr", "kkk", "ede", new Date(), new Long(1));
	}
        
	public String getCustomer() {
                Customer customer = cm.getCustomer(new Long(5)); 
                if (customer != null)
                    return customer.toString();
                else
                    return "No information about customer #5";
	}
        
        public void setCustomer(String s) {
        }
        
        public String getCustomersList() {
		return cm.getCustomersList().toString();
	}
        
        public void setCustomersList(String s) {
        }
}