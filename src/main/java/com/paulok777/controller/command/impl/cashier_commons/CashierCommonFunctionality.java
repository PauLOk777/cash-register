package com.paulok777.controller.command.impl.cashier_commons;

import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.Product;
import com.paulok777.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class CashierCommonFunctionality {
    public static void getOrders(OrderService orderService, HttpServletRequest request) {
        List<Order> orders = orderService.getOrders();
        request.setAttribute("orders", orders);
    }

    public static void getOrderById(OrderService orderService, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        Map<Long, Product> products = orderService.getProductsByOrderId(id);
        session.setAttribute("orderId", id);
        session.setAttribute("products", products);
    }

    public static long createNewOrder(OrderService orderService, HttpServletRequest request) {
        return orderService.saveNewOrder((String) request.getSession().getAttribute("username"));
    }

    public static void closeOrder(OrderService orderService, HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        orderService.makeStatusClosed(id);
    }
}
