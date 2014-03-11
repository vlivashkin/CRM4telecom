package com.crm4telecom.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserManager implements UserManagerLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean login(String login, String password) {
        String sqlQuery = "SELECT u.password FROM Users u WHERE u.login = :login";
        System.out.println(em == null);
        
        Query query = em.createQuery(sqlQuery).setParameter("login", login);
        if (query.getResultList().size() > 0) {
            String pass = (String) query.getResultList().get(0);
            System.out.println(sqlQuery);
            System.out.println(pass);
            if (pass != null && pass.equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        
    }
}
