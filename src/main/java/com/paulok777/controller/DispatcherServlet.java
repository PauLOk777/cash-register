package com.paulok777.controller;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.*;
import com.paulok777.controller.command.impl.cashier.*;
import com.paulok777.controller.command.impl.commodity_expert.ChangeAmountOfProductCommand;
import com.paulok777.controller.command.impl.commodity_expert.CreateProductCommand;
import com.paulok777.controller.command.impl.commodity_expert.GetProductsCommand;
import com.paulok777.controller.command.impl.senior_cashier.*;
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
    private static final String COMMAND_NOT_FOUND = "COMMAND NOT FOUND";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        config.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        putGetCommands(serviceFactory);
        putPostCommands(serviceFactory);
    }

    private void putGetCommands(ServiceFactory serviceFactory) {
        getCommands.put("index", new IndexCommand());
        getCommands.put("logout", new LogOutCommand());
        getCommands.put("login", new GetLoginPageCommand());
        getCommands.put("registration", new GetRegistrationPageCommand());
        getCommands.put("commodity_expert/products", new GetProductsCommand());
        getCommands.put("cashier/orders", new GetOrdersCashierCommand());
        getCommands.put("cashier/orders/\\d+", new GetOrderByIdCashierCommand());
        getCommands.put("senior_cashier/orders", new GetOrdersSeniorCashierCommand());
        getCommands.put("senior_cashier/orders/\\d+", new GetOrderByIdSeniorCashierCommand());
        getCommands.put("senior_cashier/reports/x", new MakeXReportCommand());
        getCommands.put("senior_cashier/reports/z", new MakeZReportCommand());
    }

    private void putPostCommands(ServiceFactory serviceFactory) {
        postCommands.put("login", new LogInCommand(serviceFactory.createUserService()));
        postCommands.put("registration", new RegistrationCommand(serviceFactory.createUserService()));
        postCommands.put("commodity_expert/products", new CreateProductCommand());
        postCommands.put("commodity_expert/products/\\d+", new ChangeAmountOfProductCommand());
        postCommands.put("cashier/orders", new CreateNewOrderCashierCommand());
        postCommands.put("cashier/orders/\\d+", new AddProductCashierCommand());
        postCommands.put("cashier/orders/\\d+/\\d+", new ChangeAmountOfProductCashierCommand());
        postCommands.put("cashier/orders/close/\\d+", new CloseOrderCashierCommand());
        postCommands.put("senior_cashier/orders", new CreateNewOrderSeniorCashierCommand());
        postCommands.put("senior_cashier/orders/\\d+", new AddProductSeniorCashierCommand());
        postCommands.put("senior_cashier/orders/\\d+/\\d+", new ChangeAmountOfProductSeniorCashierCommand());
        postCommands.put("senior_cashier/orders/close/\\d+", new CloseOrderSeniorCashierCommand());
        postCommands.put("senior_cashier/orders/cancel/\\d+", new CancelOrderCommand());
        postCommands.put("senior_cashier/orders/cancel/\\d+/\\d+", new CancelProductInOrderCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, getCommands);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, postCommands);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp, Map<String, Command> commands)
            throws IOException, ServletException {
        String path = req.getRequestURI();
        path = path.replaceFirst("/", "");
        String key = commands
                .keySet()
                .stream()
                .filter(path::matches)
                .findFirst()
                .orElse(COMMAND_NOT_FOUND);

        if (key.equals(COMMAND_NOT_FOUND)) {
            resp.sendError(403);
            return;
        }
        Command command = commands.get(key);
        String result = command.execute(req);
        if (result.contains(REDIRECT)) {
            resp.sendRedirect(result.replace(REDIRECT, ""));
        } else {
            req.getRequestDispatcher(result).forward(req, resp);
        }
    }
}
