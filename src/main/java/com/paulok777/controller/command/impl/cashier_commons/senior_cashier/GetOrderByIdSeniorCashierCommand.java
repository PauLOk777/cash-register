package com.paulok777.controller.command.impl.cashier_commons.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.entity.Product;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class GetOrderByIdSeniorCashierCommand implements Command {
    private final OrderService orderService;

    public GetOrderByIdSeniorCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CashierCommonFunctionality.getOrderById(orderService, request);
        return "/WEB-INF/senior_cashier/orderProductsSeniorCashier.jsp";
    }
}
