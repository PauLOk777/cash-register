package com.paulok777.controller.command.impl;

import com.paulok777.controller.command.Command;
import com.paulok777.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

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
        logger.info("Removed from logged users - " + userName);
        return "redirect:/";
    }
}
