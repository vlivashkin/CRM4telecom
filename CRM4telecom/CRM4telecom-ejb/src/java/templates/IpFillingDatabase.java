package templates;

import com.crm4telecom.ejb.GetManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class IpFillingDatabase extends FillingDatabase {

    @EJB
    private GetManagerLocal gm;

    @Override
    protected void GetDataAndFill(Long customerId) {
        gm.getFreeIp(customerId);
    }
}
