package com.paulok777.controller.command.impl.cashier_commons.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.entity.Order;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetOrdersSeniorCashierCommand implements Command {
    private final OrderService orderService;

    public GetOrdersSeniorCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CashierCommonFunctionality.getOrders(orderService, request);
        return "/WEB-INF/senior_cashier/ordersSeniorCashier.jsp";
    }
}
