package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.GetManagerLocal;
import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.enums.UserType;
import com.crm4telecom.jpa.User;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Named
@Dependent
public class UserValidationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    UserManagerLocal um;

    @EJB
    GetManagerLocal gm;

    @Size(min = 1, max = 30)
    private String login;

    @Size(min = 1, max = 30)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @Size(min = 1, max = 30)
    private String employee;

    private Long employeeId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        employeeId = Long.parseLong(employee.substring(1, employee.indexOf(" ")));
        this.employee = employee;
    }

    public List<String> completeEmployee(String query) {
        return gm.completeEmployee(query);
    }

    public String create() {
        User u = new User();
        u.setLogin(login);
        u.setPassword(password);
        u.setType(type);
        u.setEmployeeId(gm.getEmployee(employeeId));
        um.create(u);
        return "index?faces-redirect=true";
    }

}
