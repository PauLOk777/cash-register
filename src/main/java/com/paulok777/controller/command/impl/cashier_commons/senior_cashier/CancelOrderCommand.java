package com.paulok777.controller.command.impl.cashier_commons.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CancelOrderCommand implements Command {
    private final OrderService orderService;
    private static final Logger logger = LogManager.getLogger(CancelOrderCommand.class);

    public CancelOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        logger.warn("Cancel order (id: {})", id);
        orderService.cancelOrder(id);
        return "redirect:/senior_cashier/orders";
    }
}
