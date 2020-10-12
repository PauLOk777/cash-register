package com.paulok777.controller.filter;

import com.paulok777.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        if (session.getAttribute("role") == null) {
            setGuestRole(session);
        }

        User.Role role = User.Role.valueOf(session.getAttribute("role").toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setGuestRole(HttpSession session) {
        session.setAttribute("role", User.Role.UNKNOWN.toString());
    }

    @Override
    public void destroy() {

    }
}
