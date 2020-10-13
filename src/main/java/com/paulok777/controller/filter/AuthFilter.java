package com.paulok777.controller.filter;

import com.paulok777.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final String CASHIER_DOMAIN = "/cashier";
    private static final String SENIOR_CASHIER_DOMAIN = "/senior_cashier";
    private static final String COMMODITY_EXPERT_DOMAIN = "/commodity_expert";
    private static final String GUEST_DOMAIN = "^(/)$|^(/login)$|^(/registration)$";
    private static final String LOGOUT_DOMAIN = "/logout";

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
        if (!checkAccess(req.getRequestURI(), role)) {
            if (role.equals(User.Role.UNKNOWN)) {
                resp.sendRedirect("/login");
                return;
            }
            resp.sendError(403);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setGuestRole(HttpSession session) {
        session.setAttribute("role", User.Role.UNKNOWN.toString());
    }

    private boolean checkAccess(String uri, User.Role role) {
        switch (role) {
            case CASHIER:
                return checkCashierAccess(uri);
            case SENIOR_CASHIER:
                return checkSeniorCashierAccess(uri);
            case COMMODITY_EXPERT:
                return checkCommodityExpertAccess(uri);
            default:
                return checkGuestAccess(uri);
        }
    }

    private boolean checkCashierAccess(String uri) {
        return uri.startsWith(CASHIER_DOMAIN) || uri.equals(LOGOUT_DOMAIN);
    }

    private boolean checkSeniorCashierAccess(String uri) {
        return uri.startsWith(SENIOR_CASHIER_DOMAIN) || uri.equals(LOGOUT_DOMAIN);
    }

    private boolean checkCommodityExpertAccess(String uri) {
        return uri.startsWith(COMMODITY_EXPERT_DOMAIN) || uri.equals(LOGOUT_DOMAIN);
    }

    private boolean checkGuestAccess(String uri) {
        return uri.matches(GUEST_DOMAIN);
    }

    @Override
    public void destroy() {

    }
}
