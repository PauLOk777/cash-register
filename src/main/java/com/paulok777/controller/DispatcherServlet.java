package com.paulok777.controller;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.*;
import com.paulok777.model.service.ServiceFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet extends HttpServlet {
    private final Map<String, Command> getCommands = new ConcurrentHashMap<>();
    private final Map<String, Command> postCommands = new ConcurrentHashMap<>();
    private static final String REDIRECT = "redirect:";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        config.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
    }

    private void putGetCommands() {
        getCommands.put("index", new IndexCommand());
        getCommands.put("logout", new LogOutCommand());
        getCommands.put("login", new GetLoginPageCommand());
        getCommands.put("registration", new GetRegistrationPageCommand());
    }

    private void putPostCommands() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        postCommands.put("login", new LogInCommand(serviceFactory.createUserService()));
        postCommands.put("registration", new RegistrationCommand(serviceFactory.createUserService()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
