package com.crm4telecom.web.beans;

import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.jpa.User;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class UserListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private UserManagerLocal um;

    private User selected;

    public User getSelected() {
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    public List<User> getUsers() {
        return um.getUsers();
    }
}
