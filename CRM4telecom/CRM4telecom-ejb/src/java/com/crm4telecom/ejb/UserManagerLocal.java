package com.crm4telecom.ejb;

import com.crm4telecom.jpa.User;
import javax.ejb.Local;
import java.util.List;

@Local
public interface UserManagerLocal {

    User login(String login, String password);

    List<String> getLogins();
    
    List<User> getUsers();

    void create(User u);
}
