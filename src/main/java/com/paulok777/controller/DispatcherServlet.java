package com.paulok777.controller;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.*;
import com.paulok777.controller.command.impl.cashier_commons.cashier.*;
import com.paulok777.controller.command.impl.commodity_expert.ChangeAmountOfProductCommand;
import com.paulok777.controller.command.impl.commodity_expert.CreateProductCommand;
import com.paulok777.controller.command.impl.commodity_expert.GetProductsCommand;
import com.paulok777.controller.command.impl.guest.*;
import com.paulok777.controller.command.impl.cashier_commons.senior_cashier.*;
import com.paulok777.controller.util.ResourceManager;
import com.paulok777.model.exception.CashRegisterException;
import com.paulok777.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet extends HttpServlet {
    private final Map<String, Command> getCommands = new ConcurrentHashMap<>();
    private final Map<String, Command> postCommands = new ConcurrentHashMap<>();
    private static final String REDIRECT = "redirect:";
    private static final String COMMAND_NOT_FOUND = "COMMAND NOT FOUND";
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        config.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        putGetCommands(serviceFactory);
        putPostCommands(serviceFactory);
        logger.info("All commands were initialized");
    }

    private void putGetCommands(ServiceFactory serviceFactory) {
        getCommands.put("", new IndexCommand());
        getCommands.put("logout", new LogOutCommand());
        getCommands.put("login", new GetLoginPageCommand());
        getCommands.put("registration", new GetRegistrationPageCommand());
        getCommands.put("commodity_expert/products", new GetProductsCommand(serviceFactory.createProductService()));
        getCommands.put("cashier/orders", new GetOrdersCashierCommand(serviceFactory.createOrderService()));
        getCommands.put("cashier/orders/\\d+", new GetOrderByIdCashierCommand(serviceFactory.createOrderService()));
        getCommands.put("senior_cashier/orders", new GetOrdersSeniorCashierCommand(serviceFactory.createOrderService()));
        getCommands.put("senior_cashier/orders/\\d+", new GetOrderByIdSeniorCashierCommand(serviceFactory.createOrderService()));
        getCommands.put("senior_cashier/reports/x", new MakeXReportCommand(serviceFactory.createOrderService()));
        getCommands.put("senior_cashier/reports/z", new MakeZReportCommand(serviceFactory.createOrderService()));
    }

    private void putPostCommands(ServiceFactory serviceFactory) {
        postCommands.put("login", new LogInCommand(serviceFactory.createUserService()));
        postCommands.put("registration", new RegistrationCommand(serviceFactory.createUserService()));
        postCommands.put("commodity_expert/products", new CreateProductCommand(serviceFactory.createProductService()));
        postCommands.put("commodity_expert/products/\\d+", new ChangeAmountOfProductCommand(serviceFactory.createProductService()));
        postCommands.put("cashier/orders", new CreateNewOrderCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("cashier/orders/\\d+", new AddProductCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("cashier/orders/\\d+/\\d+", new ChangeAmountOfProductCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("cashier/orders/close/\\d+", new CloseOrderCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("senior_cashier/orders", new CreateNewOrderSeniorCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("senior_cashier/orders/\\d+", new AddProductSeniorCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("senior_cashier/orders/\\d+/\\d+", new ChangeAmountOfProductSeniorCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("senior_cashier/orders/close/\\d+", new CloseOrderSeniorCashierCommand(serviceFactory.createOrderService()));
        postCommands.put("senior_cashier/orders/cancel/\\d+", new CancelOrderCommand(serviceFactory.createOrderService()));
        postCommands.put("senior_cashier/orders/cancel/\\d+/\\d+", new CancelProductInOrderCommand(serviceFactory.createOrderService()));
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
        logger.info("(username: {}) request uri: {}", req.getSession().getAttribute("username"), path);
        path = path.replaceFirst("/", "");
        String key = commands
                .keySet()
                .stream()
                .filter(path::matches)
                .findFirst()
                .orElse(COMMAND_NOT_FOUND);
        logger.info("Command key: {}", key);

        if (key.equals(COMMAND_NOT_FOUND)) {
            logger.warn("(username: {}) error 404: page not found", req.getSession().getAttribute("username"));
            resp.sendError(404);
            return;
        }

        Command command = commands.get(key);
        String result;
        try {
             result = command.execute(req);
        } catch (CashRegisterException e) {
            Locale locale = req.getSession().getAttribute("lang").toString().equals("en")
                    ? Locale.ENGLISH
                    : new Locale("ua");
            resp.getWriter().print(ResourceManager.getMessage(e.getMessage(), locale));
            logger.error("(username: {}) cash register exception: {}",
                    req.getSession().getAttribute("username"), e.getMessage());
            return;
        } catch (NumberFormatException e) {
            logger.error("(username: {}) number format exception (bad request): {}",
                    req.getSession().getAttribute("username"), e.getMessage());
            resp.sendError(400);
            return;
        }

        if (result.contains(REDIRECT)) {
            resp.sendRedirect(result.replace(REDIRECT, ""));
        } else {
            req.getRequestDispatcher(result).forward(req, resp);
        }
    }
}
