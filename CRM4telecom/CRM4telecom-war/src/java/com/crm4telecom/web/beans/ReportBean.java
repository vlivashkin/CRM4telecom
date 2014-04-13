package com.crm4telecom.web.beans;

import java.io.Serializable;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class ReportBean implements Serializable {

    String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
