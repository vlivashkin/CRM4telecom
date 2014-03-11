package com.crm4telecom.web.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
 
public class SessionListener implements HttpSessionListener {
 
  private static int totalActiveSessions;
 
  public static int getTotalActiveSession(){
	return totalActiveSessions;
  }
 
  @Override
  public void sessionCreated(HttpSessionEvent evt) {
	totalActiveSessions++;
	System.out.println("sessionCreated - totalActiveSessions = " + totalActiveSessions);
        HttpSession session = evt.getSession();
        Long userId = SessionUtils.getUserId(session);
        if (userId == null) {
    //        SessionUtils.setUserId(session, Application.getGuestUserId());
        }
        System.out.println("currentUserId = " + SessionUtils.getUserId(session));
  }
 
  @Override
  public void sessionDestroyed(HttpSessionEvent evt) {
	totalActiveSessions--;
	System.out.println("sessionDestroyed - totalActiveSessions = " + totalActiveSessions);
  }	
}
