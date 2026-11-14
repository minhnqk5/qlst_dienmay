package com.qlst.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/manager/*")
public class AuthFilter implements Filter {

    public static final String SESSION_MANAGER = "manager";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_MANAGER) == null) {
            ((HttpServletResponse) resp).sendRedirect(request.getContextPath() + "/login");
            return;
        }
        ((HttpServletResponse) resp).setHeader("Cache-Control", "no-store");
        chain.doFilter(req, resp);
    }
}
