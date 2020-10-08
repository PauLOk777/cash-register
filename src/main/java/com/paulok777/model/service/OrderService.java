package com.paulok777.model.service;

import com.paulok777.model.dto.ReportDTO;
import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.Order.OrderStatus;
import com.paulok777.model.entity.OrderProducts;
import com.paulok777.model.entity.Product;
import com.paulok777.model.exception.cash_register_exc.InvalidIdException;
import com.paulok777.model.exception.cash_register_exc.order_exc.IllegalOrderStateException;
import com.paulok777.model.exception.cash_register_exc.order_exc.NoSuchProductException;
import com.paulok777.model.exception.cash_register_exc.product_exc.NotEnoughProductsException;
//import com.paulok777.repository.OrderRepository;
import com.paulok777.controller.util.ExceptionKeys;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
//    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(final UserService userService, final ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

//    public List<Order> getOrders() {
//        return orderRepository.findByStatusOrderByCreateDateDesc(OrderStatus.NEW);
//    }

//    public Order saveNewOrder() {
//        return orderRepository.save(
//                Order.builder()
//                        .totalPrice(0L)
//                        .createDate(LocalDateTime.now())
//                        .status(OrderStatus.NEW)
//                        .user(userService.getCurrentUser())
//                        .orderProducts(new ArrayList<>())
//                        .build()
//        );
//    }

    public Map<Long, Product> getProductsByOrderId(String id) {
        Order order = getOrderById(id);
        if (!order.getStatus().equals(OrderStatus.NEW)) {
//            log.warn("(username: {}) {}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.ILLEGAL_ORDER_STATE);
            throw new IllegalOrderStateException(ExceptionKeys.ILLEGAL_ORDER_STATE);
        }

         return order.getOrderProducts()
                .stream()
                .filter(orderProducts -> orderProducts.getAmount() > 0)
                .sorted(Comparator.comparing(op -> op.getProduct().getName()))
                .collect(Collectors.toMap(
                        OrderProducts::getAmount, OrderProducts::getProduct,
                        (x, y) -> y, LinkedHashMap::new));
    }

//    @Transactional
    public void addProductToOrderByCodeOrName(String orderId, String productIdentifier, Long amount) {
        Order order = getOrderById(orderId);
        Product product = productService.findByIdentifier(productIdentifier);

        addProductToOrder(amount, order, product);

//        orderRepository.save(order);
        productService.saveProduct(product);
    }

    private void addProductToOrder(Long amount, Order order, Product product) {
        checkProductAmount(amount, product.getAmount());

        OrderProducts orderProducts = createOrderProducts(amount, order, product);

        order.setTotalPrice(order.getTotalPrice() + product.getPrice() * amount);
        product.setAmount(product.getAmount() - amount);

        order.getOrderProducts().add(orderProducts);
    }

    private OrderProducts createOrderProducts(Long amount, Order order, Product product) {
        OrderProducts orderProducts = getOrderProducts(order, product);

        if (orderProducts.getAmount() > 0) {
            order.getOrderProducts().remove(orderProducts);
            orderProducts.setAmount(amount + orderProducts.getAmount());
        } else {
            orderProducts.setAmount(amount);
        }

        return orderProducts;
    }

//    @Transactional
    public void changeAmountOfProduct(String orderId, String productId, Long amount) {
        Order order = getOrderById(orderId);
        Product product = getProductById(productId);

        OrderProducts orderProducts = parseOptionalAndThrowInvalidId(getOptionalOrderProducts(order, product));

        checkIllegalOrderStateProductCanceled(orderProducts);
        checkProductAmount(amount, orderProducts.getAmount() + product.getAmount());

        calculateDataAfterChangingAmount(amount, order, product, orderProducts);

//        orderRepository.save(order);
    }

    private void calculateDataAfterChangingAmount(Long amount, Order order, Product product, OrderProducts orderProducts) {
        long differenceInAmount = amount - orderProducts.getAmount();
        long differenceIntPrice = differenceInAmount * product.getPrice();

        order.getOrderProducts().remove(orderProducts);

        orderProducts.setAmount(amount);

        order.setTotalPrice(order.getTotalPrice() + differenceIntPrice);
        product.setAmount(product.getAmount() - differenceInAmount);

        order.getOrderProducts().add(orderProducts);
    }

    private void checkIllegalOrderStateProductCanceled(OrderProducts orderProducts) {
        if (orderProducts.getAmount() < 1) {
//            log.warn("(username: {}) {}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.ILLEGAL_ORDER_STATE_PRODUCT_CANCELED);
            throw new NoSuchProductException(ExceptionKeys.ILLEGAL_ORDER_STATE_PRODUCT_CANCELED);
        }
    }

    private void checkProductAmount(Long amount, Long productAmount) {
        if (productAmount < amount) {
//            log.warn("(username: {}) {}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.NOT_ENOUGH_PRODUCTS);
            throw new NotEnoughProductsException(ExceptionKeys.NOT_ENOUGH_PRODUCTS);
        }
    }

    public void makeStatusClosed(String id) {
        try {
//            orderRepository.changeStatusToClosed(Long.valueOf(id), OrderStatus.CLOSED);
        } catch (NumberFormatException e) {
//            log.warn("(username: {}) {}}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        }
    }

//    @Transactional
    public void cancelOrder(String id) {
        Order order = getOrderById(id);

        order.getOrderProducts().forEach(orderProducts -> {
            Product product = orderProducts.getProduct();
            product.setAmount(product.getAmount() + orderProducts.getAmount());
            productService.saveProduct(product);
        });

        order.setStatus(OrderStatus.CANCELED);
//        orderRepository.save(order);
    }

//    @Transactional
    public void cancelProduct(String orderId, String productId) {
        Order order = getOrderById(orderId);
        Product product = getProductById(productId);

        OrderProducts orderProducts = parseOptionalAndThrowInvalidId(getOptionalOrderProducts(order, product));

        order.getOrderProducts().remove(orderProducts);

        order.setTotalPrice(order.getTotalPrice() - product.getPrice() * orderProducts.getAmount());
        product.setAmount(product.getAmount() + orderProducts.getAmount());
        orderProducts.setAmount(0L);

        order.getOrderProducts().add(orderProducts);

//        orderRepository.save(order);
    }

    public Order getOrderById(String id) {
        try {
//            return parseOptionalAndThrowInvalidId(orderRepository.findById(Long.parseLong(id)));
            return null;
        } catch (NumberFormatException e) {
//            log.warn("(username: {}) {}}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        }
    }

    public Product getProductById(String id) {
        try {
            return parseOptionalAndThrowInvalidId(productService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e) {
//            log.warn("(username: {}) {}}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        }
    }

    private <T> T parseOptionalAndThrowInvalidId(Optional<T> optional) {
        return optional.orElseThrow(() -> {
//            log.warn("(username: {}) {}}.",
//                    userService.getCurrentUser().getUsername(), ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        });
    }

    private Optional<OrderProducts> getOptionalOrderProducts(Order order, Product product) {
        return order
                .getOrderProducts()
                .stream()
                .filter(orderProduct -> product.getId().equals(orderProduct.getProduct().getId()))
                .findFirst();
    }

    private OrderProducts getOrderProducts(Order order, Product product) {
        return order
                .getOrderProducts()
                .stream()
                .filter(orderProduct -> product.getId().equals(orderProduct.getProduct().getId()))
                .findFirst().orElseGet(() -> {
                    OrderProducts orderProducts = new OrderProducts();
                    orderProducts.setOrder(order);
                    orderProducts.setProduct(product);
                    orderProducts.setAmount(0L);
                    return orderProducts;
                });
    }

    public ReportDTO makeXReport() {
        return getReportInfo(getClosedOrders());
    }

    public ReportDTO makeZReport() {
        List<Order> orders = getClosedOrders();
        archiveOrders(orders);
        return getReportInfo(orders);
    }

    private List<Order> getClosedOrders() {
//        return orderRepository.findByStatus(OrderStatus.CLOSED);
        return null;
    }

    private ReportDTO getReportInfo(List<Order> orders) {
        return ReportDTO.builder()
                .amount((long) orders.size())
                .totalPrice(
                        orders.stream()
                        .map(Order::getTotalPrice)
                        .reduce(0L, Long::sum))
                .build();
    }

//    @Transactional
    public void archiveOrders(List<Order> orders) {
        orders.forEach(order -> {
            order.setStatus(OrderStatus.ARCHIVED);
//            orderRepository.save(order);
        });
    }
}