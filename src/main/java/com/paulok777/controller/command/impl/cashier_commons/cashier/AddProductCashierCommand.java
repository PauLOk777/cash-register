package com.paulok777.controller.command.impl.cashier_commons.cashier;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.cashier_commons.CashierCommonFunctionality;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class AddProductCashierCommand implements Command {
    private final OrderService orderService;

    public AddProductCashierCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        CashierCommonFunctionality.addProductToOrder(orderService, id,
                request.getParameter("productIdentifier"), Long.valueOf(request.getParameter("amount")));
        return "redirect:/cashier/orders/" + id;
    }
}
