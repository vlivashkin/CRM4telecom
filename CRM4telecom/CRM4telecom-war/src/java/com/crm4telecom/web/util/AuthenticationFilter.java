package com.crm4telecom.web.util;

import com.crm4telecom.ejb.UserManagerLocal;
import java.io.*;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/content/*")
public final class AuthenticationFilter implements Filter {

    @EJB
    private UserManagerLocal um;

    @Override
    public void init(FilterConfig filterConfig) {
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
        String userType = (String) req.getSession().getAttribute("userType");
        StringBuffer requestURL = req.getRequestURL();
        if (req.getQueryString() != null) {
            requestURL.append("?").append(req.getQueryString());
        }
        String path = requestURL.toString();

        HttpServletResponse res = (HttpServletResponse) response;

        if (path.contains("login")) {
            if (login != null && um.getLogins().contains(login)) {
                res.sendRedirect("/CRM4telecom/content/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (login != null) {
                if (path.contains("user") && !userType.equals("ADMIN")) {
                    res.sendRedirect("/CRM4telecom/signin.xhtml");
                } else {
                    if (um.getLogins().contains(login)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("/CRM4telecom/signin.xhtml");
                    }
                }
            } else {
                res.sendRedirect("/CRM4telecom/signin.xhtml");
            }
        }
    }
}
