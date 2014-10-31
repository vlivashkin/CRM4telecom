package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.enums.UserType;
import com.crm4telecom.jpa.User;
import com.crm4telecom.web.util.JSFHelper;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(UserBean.class);

    @EJB
    private UserManagerLocal um;

    private String uname;
    private String password;

    private User user;

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
        user = um.login(uname, password);
        logger.info("Login to system by login : " + uname);
        if (user != null) {
            JSFHelper helper = new JSFHelper();
            HttpSession session = helper.getSession(false);
            session.setAttribute("login", uname);
            session.setAttribute("userType", user.getType().name());
            return "/content/index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Username or password is incorrect", "Please Try Again"));
            return null;
        }
    }

    public Boolean isLogged() {
        JSFHelper helper = new JSFHelper();
        HttpServletRequest req = helper.getRequest();
        String login = (String) req.getSession().getAttribute("login");

        return login != null;
    }

    public Boolean getIsAdmin() {
        if (user != null) {
            return user.getType().equals(UserType.ADMIN);
        }
        return false;
    }

    public void logout() {
        JSFHelper helper = new JSFHelper();
        HttpSession session = helper.getSession(false);
        session.invalidate();

        helper.redirect("index");
    }
}
