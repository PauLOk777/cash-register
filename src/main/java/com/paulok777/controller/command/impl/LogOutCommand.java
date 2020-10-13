package com.paulok777.controller.command.impl;

import com.paulok777.controller.command.Command;
import com.paulok777.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String userName = (String) session.getAttribute("username");
        CommandUtility.setUserRole(request, User.Role.UNKNOWN, "Guest");
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        loggedUsers.remove(userName);
        context.setAttribute("loggedUsers", loggedUsers);
        return "redirect:/";
    }
}
