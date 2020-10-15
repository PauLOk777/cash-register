package com.paulok777.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) se
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        String userName = (String) se.getSession().getAttribute("username");
        loggedUsers.remove(userName);
        se.getSession().setAttribute("loggedUsers", loggedUsers);
        logger.info("Removed from logged users - " + userName);
    }
}
