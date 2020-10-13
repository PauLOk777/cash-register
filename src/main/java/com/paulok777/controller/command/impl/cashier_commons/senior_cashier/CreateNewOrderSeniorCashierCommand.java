package com.paulok777.controller.command.impl.cashier_commons.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class CreateNewOrderSeniorCashierCommand implements Command {
    private final OrderService orderService;

    public CreateNewOrderSeniorCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return "redirect:/senior_cashier/orders/" + CashierCommonFunctionality.createNewOrder(orderService, request);
    }
}
