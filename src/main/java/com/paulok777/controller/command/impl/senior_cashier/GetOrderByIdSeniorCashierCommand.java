package com.paulok777.controller.command.impl.senior_cashier;

import com.paulok777.controller.command.Command;
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
        HttpSession session = request.getSession();
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        Map<Long, Product> products = orderService.getProductsByOrderId(id);
        session.setAttribute("orderId", id);
        session.setAttribute("products", products);
        return "/WEB-INF/senior_cashier/orderProductsSeniorCashier.jsp";
    }
}
