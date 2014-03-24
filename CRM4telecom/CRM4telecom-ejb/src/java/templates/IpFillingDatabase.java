
package templates;

import com.crm4telecom.ejb.IpManager;
import com.crm4telecom.ejb.IpManagerLocal;
import com.crm4telecom.jpa.Customer;
import com.crm4telecom.jpa.StaticIp;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IpFillingDatabase  extends FillingDatabase{
    @EJB
    private IpManagerLocal ipManager;

    
    @Override
    protected void GetDataAndFill(Long customerId) {
        ipManager = new IpManager();
        ipManager.getFreeIp(customerId);
    
    }
    
    
}
