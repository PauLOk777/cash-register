package com.paulok777.model.service;

import com.paulok777.model.dao.DaoFactory;
import com.paulok777.model.dao.OrderDao;
import com.paulok777.model.dto.ReportDTO;
import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.Order.OrderStatus;
import com.paulok777.model.entity.OrderProducts;
import com.paulok777.model.entity.Product;
import com.paulok777.model.exception.cash_register_exc.InvalidIdException;
import com.paulok777.model.exception.cash_register_exc.order_exc.IllegalOrderStateException;
import com.paulok777.model.exception.cash_register_exc.order_exc.NoSuchProductException;
import com.paulok777.model.exception.cash_register_exc.product_exc.NotEnoughProductsException;
import com.paulok777.model.util.ExceptionKeys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * The Order service.
 */
public class OrderService {
    private final DaoFactory daoFactory;
    private final ProductService productService;
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    /**
     * Instantiates a new Order service.
     *
     * @param daoFactory     the dao factory
     * @param productService the product service
     * @param userService    the user service
     */
    public OrderService(final DaoFactory daoFactory, final ProductService productService, final UserService userService) {
        this.daoFactory = daoFactory;
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public List<Order> getOrders() {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findByStatusOrderByCreateDateDesc(OrderStatus.NEW);
        }
    }

    /**
     * Save new order and return new generated id.
     *
     * @param username the username of user that creates order
     * @return id (long)
     */
    public long saveNewOrder(String username) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.createAndGetNewId(
                    Order.builder()
                            .totalPrice(0L)
                            .createDate(LocalDateTime.now())
                            .status(OrderStatus.NEW)
                            .user(userService.getUserByUsername(username))
                            .orderProducts(new HashSet<>())
                            .build()
            );
        }
    }

    /**
     * Gets products by order id and sorted by name.
     *
     * @param id the id of order
     * @return order products
     */
    public Map<Long, Product> getProductsByOrderId(String id) {
        Order order = getOrderById(id);
        if (!order.getStatus().equals(OrderStatus.NEW)) {
            logger.warn("Can't get order by id, because he hasn't status NEW");
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

    /**
     * Add product to order by code or name.
     *
     * @param orderId           the order id
     * @param productIdentifier the product identifier
     * @param amount            the amount
     */
//    @Transactional
    public void addProductToOrderByCodeOrName(String orderId, String productIdentifier, Long amount) {
        Order order = getOrderById(orderId);
        Product product = productService.findByIdentifier(productIdentifier);

        addProductToOrder(amount, order, product);

        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.updateWithRelations(order);
        }
        productService.updateProduct(product);
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

    /**
     * Change amount of product.
     *
     * @param orderId   the order id
     * @param productId the product id
     * @param amount    the amount
     */
//    @Transactional
    public void changeAmountOfProduct(String orderId, String productId, Long amount) {
        Order order = getOrderById(orderId);
        Product product = getProductById(productId);

        OrderProducts orderProducts = parseOptionalAndThrowInvalidId(getOptionalOrderProducts(order, product));

        checkIllegalOrderStateProductCanceled(orderProducts);
        checkProductAmount(amount, orderProducts.getAmount() + product.getAmount());

        calculateDataAfterChangingAmount(amount, order, product, orderProducts);

        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.updateWithRelations(order);
        }
        productService.updateProduct(product);
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
            logger.error("{}", ExceptionKeys.ILLEGAL_ORDER_STATE_PRODUCT_CANCELED);
            throw new NoSuchProductException(ExceptionKeys.ILLEGAL_ORDER_STATE_PRODUCT_CANCELED);
        }
    }

    private void checkProductAmount(Long amount, Long productAmount) {
        if (productAmount < amount) {
            logger.error("{}", ExceptionKeys.NOT_ENOUGH_PRODUCTS);
            throw new NotEnoughProductsException(ExceptionKeys.NOT_ENOUGH_PRODUCTS);
        }
    }

    /**
     * Make status closed.
     *
     * @param id the order id
     */
    public void makeStatusClosed(String id) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.changeStatusToClosed(Long.valueOf(id), OrderStatus.CLOSED);
        } catch (NumberFormatException e) {
            logger.error("{}", ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        }
    }

    /**
     * Cancel order.
     *
     * @param id the order id
     */
//    @Transactional
    public void cancelOrder(String id) {
        Order order = getOrderById(id);

        order.getOrderProducts().forEach(orderProducts -> {
            Product product = orderProducts.getProduct();
            product.setAmount(product.getAmount() + orderProducts.getAmount());
            productService.updateProduct(product);
        });

        order.setStatus(OrderStatus.CANCELED);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.update(order);
        }
    }

    /**
     * Cancel product in order.
     *
     * @param orderId   the order id
     * @param productId the product id
     */
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

        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.updateWithRelations(order);
        }
        productService.updateProduct(product);
    }

    /**
     * Gets order by id.
     *
     * @param id the order id
     * @return the order
     */
    public Order getOrderById(String id) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            Optional<Order> order = orderDao.findById(Long.parseLong(id));
            return parseOptionalAndThrowInvalidId(order);
        } catch (NumberFormatException e) {
            logger.error("{}", ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        }
    }

    /**
     * Gets product by id.
     *
     * @param id the product id
     * @return the product
     */
    public Product getProductById(String id) {
        try {
            return parseOptionalAndThrowInvalidId(productService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            logger.error("{}.", ExceptionKeys.INVALID_ID_EXCEPTION);
            throw new InvalidIdException(ExceptionKeys.INVALID_ID_EXCEPTION);
        }
    }

    private <T> T parseOptionalAndThrowInvalidId(Optional<T> optional) {
        return optional.orElseThrow(() -> {
            logger.error("{}", ExceptionKeys.INVALID_ID_EXCEPTION);
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

    /**
     * Make x report.
     *
     * @return the report dto
     */
    public ReportDTO makeXReport() {
        return getReportInfo(getClosedOrders());
    }

    /**
     * Make z report.
     *
     * @return the report dto
     */
    public ReportDTO makeZReport() {
        List<Order> orders = getClosedOrders();
        archiveOrders(orders);
        return getReportInfo(orders);
    }

    private List<Order> getClosedOrders() {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findByStatus(OrderStatus.CLOSED);
        }
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

    /**
     * Archive orders.
     *
     * @param orders the orders
     */
//    @Transactional
    public void archiveOrders(List<Order> orders) {
        orders.forEach(order -> {
            order.setStatus(OrderStatus.ARCHIVED);
            try (OrderDao orderDao = daoFactory.createOrderDao()) {
                orderDao.update(order);
            }
        });
    }
}
