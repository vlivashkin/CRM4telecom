package com.crm4telecom.web.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {

    private final Logger logger = LoggerFactory.getLogger(SessionListener.class);
    private static int totalActiveSessions;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent evt) {
        totalActiveSessions++;
        logger.info("sessionCreated - totalActiveSessions = " + totalActiveSessions);
        HttpSession session = evt.getSession();
        Long userId = SessionUtils.getUserId(session);
        if (userId == null) {
            //        SessionUtils.setUserId(session, Application.getGuestUserId());
        }
        logger.info("currentUserId = " + SessionUtils.getUserId(session));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent evt) {
        totalActiveSessions--;
        logger.info("sessionDestroyed - totalActiveSessions = " + totalActiveSessions);
    }
}
