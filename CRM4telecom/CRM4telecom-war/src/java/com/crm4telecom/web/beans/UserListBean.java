package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.jpa.Users;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import org.omnifaces.cdi.ViewScoped;

@ManagedBean
@ViewScoped
public class UserListBean implements Serializable {

    @EJB
    private UserManagerLocal um;

    private Users selected;

    public Users getSelected() {
        return selected;
    }

    public void setSelected(Users selected) {
        this.selected = selected;
    }

    public List<Users> getUsers() {
        return um.getUsers();
    }
}
