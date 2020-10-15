package com.paulok777.controller.command.impl.cashier_commons;

import com.paulok777.controller.util.Validator;
import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.Product;
import com.paulok777.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class CashierCommonFunctionality {
    private static final Logger logger = LogManager.getLogger(CashierCommonFunctionality.class);

    public static void getOrders(OrderService orderService, HttpServletRequest request) {
        List<Order> orders = orderService.getOrders();
        request.setAttribute("orders", orders);
    }

    public static void getOrderById(OrderService orderService, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        logger.info("get order by id: {}", id);
        Map<Long, Product> products = orderService.getProductsByOrderId(id);
        session.setAttribute("orderId", id);
        session.setAttribute("products", products);
    }

    public static long createNewOrder(OrderService orderService, HttpServletRequest request) {
        return orderService.saveNewOrder((String) request.getSession().getAttribute("username"));
    }

    public static void addProductToOrder(OrderService orderService, String id, String productIdentifier, Long amount) {
        logger.info("Add {} products (identifier: {}) to order (id: {})", amount, productIdentifier, id);
        Validator.validateAmountForCashier(amount);
        orderService.addProductToOrderByCodeOrName(id, productIdentifier, amount);
    }

    public static void changeAmountOfProduct(OrderService orderService, String orderId, String productId, Long amount) {
        logger.info("Change product (id: {}) amount in order (id: {}) to {}", productId, orderId, amount);
        Validator.validateAmountForCashier(amount);
        orderService.changeAmountOfProduct(orderId, productId, amount);
    }

    public static void closeOrder(OrderService orderService, HttpServletRequest request) {
        String[] subUris = request.getRequestURI().split("/");
        String id = subUris[subUris.length - 1];
        logger.info("Close order (id: {})", id);
        orderService.makeStatusClosed(id);
    }
}
