package com.paulok777.controller.command.impl.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CloseOrderSeniorCashierCommand implements Command {
    private final OrderService orderService;

    public CloseOrderSeniorCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        orderService.makeStatusClosed(id);
        return "redirect:/senior_cashier/orders";
    }
}
