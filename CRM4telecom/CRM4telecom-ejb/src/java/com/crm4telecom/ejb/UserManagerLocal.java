package com.crm4telecom.ejb;

import javax.ejb.Local;

@Local
public interface UserManagerLocal {
    
    boolean login(String login,String password);
}
