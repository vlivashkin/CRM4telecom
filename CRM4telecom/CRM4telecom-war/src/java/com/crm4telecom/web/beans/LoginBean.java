
package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.UserManager;
import com.crm4telecom.ejb.UserManagerLocal;import com.crm4telecom.web.util.Util;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EJB
    private UserManagerLocal um;
    private String password;
    private String message, uname;
    
    @PostConstruct
    public void init() {
    }
    
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUname() {
        return uname;
    }
 
    public void setUname(String uname) {
        this.uname = uname;
    }
 
    public String loginProject() throws ServletException {
        boolean result = um.login(uname, password);
        if (result) {
            HttpSession session = Util.getSession();
            session.setAttribute("login", uname);
            session.setAttribute("password",password);
            HttpServletRequest r =  Util.getRequest();
            return "index";
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Invalid Login!","Please Try Again!"));
            return "login";
        }
    }

    public String sss(){
        System.out.println(Util.getUserName());
        return "index";
    }
 
    public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return "login?faces-redirect=true";
   }
}
    
