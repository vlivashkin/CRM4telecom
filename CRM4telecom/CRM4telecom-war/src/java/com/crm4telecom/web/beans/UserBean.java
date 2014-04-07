package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private final Logger log = Logger.getLogger(getClass().getName());
    private static final long serialVersionUID = 1L;
    private String uname;
    private String password;

    @EJB
    private UserManagerLocal um;

    @PostConstruct
    public void init() {
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws ServletException {
        boolean result = um.login(uname, password);
        if (log.isInfoEnabled()) {
                log.info("Login to system by login : " + uname);
            }
        if (result) {
            JSFHelper helper = new JSFHelper();
            HttpSession session = helper.getSession(false);
            session.setAttribute("login", uname);
            return "/content/index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Username or password is incorrect", "Please Try Again"));
            return null;
        }
    }

    public Boolean getIsAdmin() {
        return uname.equals("admin");
    }

    public String logout() {
        JSFHelper helper = new JSFHelper();
        HttpSession session = helper.getSession(false);
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }
}
