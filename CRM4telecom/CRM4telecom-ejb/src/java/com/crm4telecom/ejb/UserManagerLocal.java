package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Users;
import javax.ejb.Local;
import java.util.List;

@Local
public interface UserManagerLocal {

    Users login(String login, String password);

    List<String> getLogins();
    
    List<Users> getUsers();

    void create(Users u);
}
