package com.paulok777.controller.command.impl.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class CreateNewOrderSeniorCashierCommand implements Command {
    private final OrderService orderService;

    public CreateNewOrderSeniorCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id = orderService.saveNewOrder((String) request.getSession().getAttribute("username"));
        return "redirect:/senior_cashier/orders/" + id;
    }
}
