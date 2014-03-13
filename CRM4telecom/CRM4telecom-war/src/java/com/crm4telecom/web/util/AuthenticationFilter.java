package com.crm4telecom.web.util;

import com.crm4telecom.ejb.UserManagerLocal;
import com.crm4telecom.web.beans.LoginBean;
import java.io.*;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public final class AuthenticationFilter implements Filter {

    private FilterConfig filterConfig = null;
    @EJB
    private UserManagerLocal um;
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String login = (String) req.getSession().getAttribute("login");
        String password = (String) req.getSession().getAttribute("password");
        StringBuffer requestURL = req.getRequestURL();
        if (req.getQueryString() != null) {
            requestURL.append("?").append(req.getQueryString());
        }
        String path = requestURL.toString();
     
       if (path.contains("login")) {
            if(login != null && um.getlogins().contains(login)) {
               HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("http://localhost:8080/CRM4telecom-war/index.xhtml");
            }else{
                 chain.doFilter(request, response);
            }
       }else{
            if (login != null) {
                if (login != null && login.equals("admin")) {
                    chain.doFilter(request, response);
                } else {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendRedirect("http://localhost:8080/CRM4telecom-war/login.xhtml");
                }
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("http://localhost:8080/CRM4telecom-war/login.xhtml");
            }
       }
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
