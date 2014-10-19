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
    
    private Long installationFee;

    private String product = "Product";
    
    private Long onetimePrice = 0L;
    private Long monthlyPrice = 0L;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getOnetimePrice() {
        if (onetimePrice == 0) return 0L;
        return onetimePrice;
    }

    public void setOnetimePrice(Long onetimePrice) {
        this.onetimePrice = onetimePrice;
    }

    public Long getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Long monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
    
    public GetManagerLocal getGm() {
        return gm;
    }

    public Long getInstallationFee() {
        if (installationFee == null) return 0L;
        return installationFee;
    }

    public void setInstallationFee(Long installationFee) {
        this.installationFee = installationFee;
    }
    
}
