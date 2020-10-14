package com.paulok777.controller.command.impl.cashier_commons.cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class ChangeAmountOfProductCashierCommand implements Command {
    private final OrderService orderService;

    public ChangeAmountOfProductCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String orderId = subUris[subUris.length - 2];
        String productId = subUris[subUris.length - 1];
        CashierCommonFunctionality.changeAmountOfProduct(orderService, orderId, productId,
                Long.valueOf(request.getParameter("amount")));
        return "redirect:/cashier/orders/" + orderId;
    }
}
