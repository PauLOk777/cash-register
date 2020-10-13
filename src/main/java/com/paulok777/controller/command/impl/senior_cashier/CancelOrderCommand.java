package com.paulok777.controller.command.impl.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class CancelOrderCommand implements Command {
    private final OrderService orderService;

    public CancelOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        orderService.cancelOrder(id);
        return "redirect:/senior_cashier/orders";
    }
}
