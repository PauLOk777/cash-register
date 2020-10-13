package com.paulok777.controller.command.impl;

import com.paulok777.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtility {
    public static void setUserRole(HttpServletRequest request,
                            User.Role role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getSession().getServletContext();
        context.setAttribute("username", name);
        session.setAttribute("username", name);
        session.setAttribute("role", role);
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String userName) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(userName::equals)) {
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }
}
