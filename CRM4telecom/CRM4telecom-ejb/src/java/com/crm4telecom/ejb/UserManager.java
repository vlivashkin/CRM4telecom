package com.crm4telecom.ejb;

import com.crm4telecom.jpa.User;
import com.crm4telecom.util.MD5;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UserManager implements UserManagerLocal {

    private final Logger logger = LoggerFactory.getLogger(UserManager.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User u) {
        try {
            List<String> l = MD5.getSaltPassword(u.getPassword(), u.getLogin());
            u.setPassword(l.get(1));
            u.setSalt(l.get(0));
            em.persist(u);
        } catch (NoSuchAlgorithmException ex) {
                logger.error("NO MD5 algoritm  ", ex);
        }
    }

    @Override
    public User login(String login, String password) {
        if (login != null && password != null) {
            String sqlQuery = "SELECT u FROM Users u WHERE u.login = :login";

            Query query = em.createQuery(sqlQuery).setParameter("login", login);
            if (query.getResultList().size() > 0) {
                User user = (User) query.getResultList().get(0);
                String pass = user.getPassword();
                String salt = user.getSalt();
                if (pass != null && pass.equals(MD5.getHash(password, salt))) {
                    return user;
                }
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Login and password can't be null");
        }
        return null;
    }

    @Override
    public List<String> getLogins() {
        String sqlQuery = "SELECT u.login FROM Users u ";
        Query query = em.createQuery(sqlQuery);
        return query.getResultList();
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "SELECT u FROM Users u ";
        Query query = em.createQuery(sqlQuery);
        return query.getResultList();
    }
}
