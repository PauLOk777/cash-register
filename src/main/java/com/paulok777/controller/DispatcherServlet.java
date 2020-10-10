package com.paulok777.controller;

import com.paulok777.controller.command.Command;

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
    private final Map<String, Command> commands = new ConcurrentHashMap<>();
    private static final String REDIRECT = "redirect:";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        config.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
    }

    private void putAllCommands() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();

        Command command = commands.getOrDefault(path,
                (r) -> "index");
        String result = command.execute(req);

        if (result.contains(REDIRECT)) {
            resp.sendRedirect(result.replace(REDIRECT, ""));
        } else {
            req.getRequestDispatcher(result).forward(req, resp);
        }
    }
}
