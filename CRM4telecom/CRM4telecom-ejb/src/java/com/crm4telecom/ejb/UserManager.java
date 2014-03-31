package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Users;
import com.crm4telecom.util.MD5;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserManager implements UserManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Users u) {
        try {
            List<String> l = MD5.getSaltPassword(u.getPassword(), u.getLogin());
            u.setPassword(l.get(1));
            u.setSalt(l.get(0));

            em.persist(u);
        } catch (NoSuchAlgorithmException ex) {

        }

    }

    @Override
    public boolean login(String login, String password) {
        if (login != null && password != null) {
            String sqlQuery = "SELECT u FROM Users u WHERE u.login = :login";

            Query query = em.createQuery(sqlQuery).setParameter("login", login);
            if (query.getResultList().size() > 0) {
                Users user = (Users) query.getResultList().get(0);
                String pass = user.getPassword();
                String salt = user.getSalt();
                System.out.println(salt);
                return pass != null && pass.equals(MD5.getHash(password, salt));
            } else {
                return false;
            }
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public List<String> getlogins() {
        String sqlQuery = "SELECT u.login FROM Users u ";
        Query query = em.createQuery(sqlQuery);
        return query.getResultList();
    }
}
