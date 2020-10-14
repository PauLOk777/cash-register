package com.paulok777.controller.command.impl.cashier_commons.senior_cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class CancelProductInOrderCommand implements Command {
    private final OrderService orderService;

    public CancelProductInOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String orderId = subUris[subUris.length - 2];
        String productId = subUris[subUris.length - 1];
        orderService.cancelProduct(orderId, productId);
        return "redirect:/senior_cashier/orders/" + orderId;
    }
}
