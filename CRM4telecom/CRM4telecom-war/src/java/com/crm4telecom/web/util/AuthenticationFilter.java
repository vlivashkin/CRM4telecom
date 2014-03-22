package com.crm4telecom.web.util;

import com.crm4telecom.ejb.UserManagerLocal;
import java.io.*;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/content/*")
public final class AuthenticationFilter implements Filter {

    private FilterConfig filterConfig = null;

    @EJB
    private UserManagerLocal um;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
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

        HttpServletResponse res = (HttpServletResponse) response;

        if (path.contains("login")) {
            if (login != null && um.getlogins().contains(login)) {
                res.sendRedirect("/CRM4telecom/content/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (login != null) {
                if (path.contains("user") && !login.equals("admin")) {
                    res.sendRedirect("/CRM4telecom/login.xhtml");
                } else {
                    if (um.getlogins().contains(login)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("/CRM4telecom/login.xhtml");
                    }
                }
            } else {
                res.sendRedirect("/CRM4telecom/login.xhtml");
            }
        }
    }
}
