package com.paulok777.controller.command.impl.cashier_commons.cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.entity.Product;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class GetOrderByIdCashierCommand implements Command {
    public final OrderService orderService;

    public GetOrderByIdCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CashierCommonFunctionality.getOrderById(orderService, request);
        return "/WEB-INF/cashier/orderProductsCashier.jsp";
    }
}
