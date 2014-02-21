package com.crm4telecom.web.beans;
 
import com.crm4telecom.ejb.ClientManagerLocal;
import java.util.Date;
import javax.inject.Inject;

public class UserBean{
    
        @Inject
        private ClientManagerLocal cm;
        
	public String goLoginPage(){
		cm.addCustomer("rr", "rrr", "kkk", "ede", new Date(), 3);
                
		return "login";
		
	}
}