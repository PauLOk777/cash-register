package com.paulok777.controller.command.impl.cashier_commons.cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class CloseOrderCashierCommand implements Command {
    private final OrderService orderService;

    public CloseOrderCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CashierCommonFunctionality.closeOrder(orderService, request);
        return "redirect:/cashier/orders";
    }
}