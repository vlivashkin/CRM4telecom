/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package templates;

import com.crm4telecom.ejb.PhoneManager;
import com.crm4telecom.ejb.PhoneManagerLocal;
import javax.ejb.EJB;

/**
 *
 * @author Alex
 */
public class PhoneFillingDatabase extends FillingDatabase{
    
    @EJB
    private PhoneManagerLocal l;
    @Override        
    protected void GetDataAndFill(Long customerId) {
        l = new PhoneManager();
        l.getFreePhone(customerId);
        
        
    }
}
