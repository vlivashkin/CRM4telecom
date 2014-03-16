package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.jpa.Users;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Size;

@ManagedBean
@SessionScoped
public class UserValidationBean implements Serializable {

    @Size(min=1, max=30)
    private String login;
    @Size(min=1, max=30)
    private String password;
    @Size(min=1, max=30)
    private String type;
    @Size(min=1, max=30)
    private String employeeid;
    @EJB
    UserManagerLocal um;
    private Users u;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String create() {
        u = new Users();
        u.setLogin(login);
        u.setPassword(password);
        u.setType(type);
        um.create(u);
        return "index?faces-redirect=true";
    }

}
