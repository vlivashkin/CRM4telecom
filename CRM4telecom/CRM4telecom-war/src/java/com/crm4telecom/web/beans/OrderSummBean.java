package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.GetManagerLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named
@Dependent
public class OrderSummBean implements Serializable {
    @EJB
    private GetManagerLocal gm;
    
    private String installationFee;

    private String product = "Product";
    
    private String onetimePrice = "0";
    private String monthlyPrice = "0";

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOnetimePrice() {
        if (onetimePrice == null) return "—";
        Long value = Long.parseLong(onetimePrice);
        if (value == 0) return "—";
        return String.valueOf(value) + ".00";
    }

    public void setOnetimePrice(String onetimePrice) {
        this.onetimePrice = onetimePrice;
    }

    public String getMonthlyPrice() {
        if (onetimePrice == null) return "—";
        Long value = Long.parseLong(monthlyPrice);
        if (value == 0L) return "—";
        return String.valueOf(value) + ".00";
    }

    public void setMonthlyPrice(String monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
    
    public GetManagerLocal getGm() {
        return gm;
    }

    public String getInstallationFee() {
        return installationFee;
    }

    public void setInstallationFee(String installationFee) {
        this.installationFee = installationFee;
    }
    
}
