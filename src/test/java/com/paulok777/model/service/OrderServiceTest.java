package com.paulok777.model.service;

import com.paulok777.model.dao.DaoFactory;
import com.paulok777.model.dao.OrderDao;
import com.paulok777.model.dto.ReportDTO;
import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.OrderProducts;
import com.paulok777.model.entity.Product;
import com.paulok777.model.entity.User;
import com.paulok777.model.exception.cash_register_exc.InvalidIdException;
import com.paulok777.model.exception.cash_register_exc.order_exc.IllegalOrderStateException;
import com.paulok777.model.exception.cash_register_exc.product_exc.NoSuchProductException;
import com.paulok777.model.exception.cash_register_exc.product_exc.NotEnoughProductsException;
import org.junit.Test;
import org.mockito.internal.matchers.Or;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class OrderServiceTest {
    DaoFactory daoFactory = mock(DaoFactory.class);
    OrderDao orderDao = mock(OrderDao.class);
    ProductService productService = mock(ProductService.class);
    UserService userService = mock(UserService.class);
    OrderService orderService = new OrderService(daoFactory, productService, userService);
    LocalDateTime localDateTime = LocalDateTime.now();

    private final Product product1 = Product.builder()
            .id(1L)
            .code("1234")
            .name("Banana")
            .price(100)
            .measure(Product.Measure.valueOf("BY_WEIGHT"))
            .amount(4000L)
            .build();

    private final Product product2 = Product.builder()
            .code("12345")
            .name("Orange")
            .price(100)
            .measure(Product.Measure.valueOf("BY_WEIGHT"))
            .amount(400L)
            .build();

    private final User user = User.builder()
            .firstName("Pavlo")
            .lastName("Trotsiuk")
            .username("PauL")
            .password("1234")
            .email("mail@mail.com")
            .phoneNumber("888888888")
            .role(User.Role.valueOf("CASHIER"))
            .build();

    private final Order orderForInsert = Order.builder()
            .id(1L)
            .status(Order.OrderStatus.NEW)
            .totalPrice(0L)
            .createDate(localDateTime)
            .build();

    private final Order order = Order.builder()
            .id(1L)
            .status(Order.OrderStatus.NEW)
            .totalPrice(100L)
            .createDate(localDateTime)
            .user(user)
            .orderProducts(new HashSet<>())
            .build();

    private final HashSet<OrderProducts> orderProducts =
            new HashSet<>(List.of(new OrderProducts(order, product1, 1L),
                    new OrderProducts(order, product2, 1L)));

    {
        order.setOrderProducts(orderProducts);
    }

    @Test
    public void testGetOrdersShouldReturnListWithOneOrderWhenOneOrderExist() {
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findByStatusOrderByCreateDateDesc(Order.OrderStatus.NEW)).thenReturn(List.of(orderForInsert));
        List<Order> orders = orderService.getOrders();
        assertEquals(orders.get(0), orderForInsert);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).findByStatusOrderByCreateDateDesc(Order.OrderStatus.NEW);
    }

    @Test
    public void testSaveNewOrderShouldReturnNewIdWhenDaoDontGenerateExceptions() {
        String username = "PauL";
        long expectedId = 1L;
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(orderDao.createAndGetNewId(any(Order.class))).thenReturn(expectedId);
        long id = orderService.saveNewOrder(username);
        assertEquals(expectedId, id);
        verify(daoFactory, times(1)).createOrderDao();
        verify(userService, times(1)).getUserByUsername(username);
        verify(orderDao, times(1)).createAndGetNewId(any(Order.class));
    }

    @Test
    public void testGetProductsByOrderIdShouldReturnLinkedHashMapWithOneElementWhenOrderWithThisIdExist() {
        String id = "1";
        long amount = 1L;
        LinkedHashMap<Long, Product> expectedProducts = new LinkedHashMap<>();
        expectedProducts.put(amount, product1);
        expectedProducts.put(amount, product2);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(id))).thenReturn(Optional.of(order));
        Map<Long, Product> products = orderService.getProductsByOrderId(id);
        assertEquals(expectedProducts, products);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(id));
    }

    @Test(expected = IllegalOrderStateException.class)
    public void testGetProductsByOrderIdShouldThrowIllegalOrderStateExceptionWhenOrderWithThisIdHasNotStatusNew() {
        String id = "1";
        order.setStatus(Order.OrderStatus.ARCHIVED);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(id))).thenReturn(Optional.of(order));
        orderService.getProductsByOrderId(id);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(id));
    }

    @Test(expected = InvalidIdException.class)
    public void testGetProductsByOrderIdShouldThrowInvalidIdExceptionWhenOrderWithThisIdAbsent() {
        String id = "2";
        order.setStatus(Order.OrderStatus.ARCHIVED);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(id))).thenReturn(Optional.empty());
        orderService.getProductsByOrderId(id);
        order.setStatus(Order.OrderStatus.NEW);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(id));
    }

    @Test(expected = InvalidIdException.class)
    public void testGetProductsByOrderIdShouldThrowInvalidIdExceptionWhenIdIsInvalid() {
        String id = "2dw";
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        orderService.getProductsByOrderId(id);
        verify(daoFactory, times(1)).createOrderDao();
    }

    @Test
    public void testAddProductToOrderByCodeOrNameShouldNotThrowExceptionsWhenOrderProductAbsent() {
        String orderId = "1";
        String productIdentifier = "Banana";
        long amount = 1;
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(orderId))).thenReturn(Optional.of(orderForTest));
        when(productService.findByIdentifier(productIdentifier)).thenReturn(productForTest);
        doNothing().when(orderDao).updateWithRelations(any(Order.class));
        doNothing().when(productService).updateProduct(any(Product.class));
        orderService.addProductToOrderByCodeOrName(orderId, productIdentifier, amount);
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(orderId));
        verify(productService, times(1)).findByIdentifier(productIdentifier);
        verify(orderDao, times(1)).updateWithRelations(any(Order.class));
        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test
    public void testAddProductToOrderByCodeOrNameShouldNotThrowExceptionsWhenOrderProductExist() {
        String orderId = "1";
        String productIdentifier = "Banana";
        long amount = 1;
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 1L));
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(orderId))).thenReturn(Optional.of(orderForTest));
        when(productService.findByIdentifier(productIdentifier)).thenReturn(productForTest);
        doNothing().when(orderDao).updateWithRelations(any(Order.class));
        doNothing().when(productService).updateProduct(any(Product.class));
        orderService.addProductToOrderByCodeOrName(orderId, productIdentifier, amount);
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(orderId));
        verify(productService, times(1)).findByIdentifier(productIdentifier);
        verify(orderDao, times(1)).updateWithRelations(any(Order.class));
        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test(expected = NotEnoughProductsException.class)
    public void testAddProductToOrderByCodeOrNameShouldThrowNotEnoughProductsExceptionWhenAmountParamIsMoreThanProductAmount() {
        String orderId = "1";
        String productIdentifier = "Banana";
        long amount = 4001L;
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(orderId))).thenReturn(Optional.of(orderForTest));
        when(productService.findByIdentifier(productIdentifier)).thenReturn(productForTest);
        orderService.addProductToOrderByCodeOrName(orderId, productIdentifier, amount);
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(orderId));
        verify(productService, times(1)).findByIdentifier(productIdentifier);
    }

    @Test
    public void testChangeAmountOfProductShouldNotThrowExceptionsWhenOrderProductExist() {
        String orderId = "1";
        String productId = "1";
        long amount = 1;
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 1L));
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(orderId))).thenReturn(Optional.of(orderForTest));
        when(productService.findById(Long.parseLong(productId))).thenReturn(Optional.of(productForTest));
        doNothing().when(orderDao).updateWithRelations(any(Order.class));
        doNothing().when(productService).updateProduct(any(Product.class));
        orderService.changeAmountOfProduct(orderId, productId, amount);
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(orderId));
        verify(productService, times(1)).findById(Long.parseLong(productId));
        verify(orderDao, times(1)).updateWithRelations(any(Order.class));
        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test(expected = NoSuchProductException.class)
    public void testChangeAmountOfProductShouldThrowNoSuchProductExceptionWhenAmountOfProductLessThanOne() {
        String orderId = "1";
        String productId = "1";
        long amount = 1;
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 0L));
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(orderId))).thenReturn(Optional.of(orderForTest));
        when(productService.findById(Long.parseLong(productId))).thenReturn(Optional.of(productForTest));
        orderService.changeAmountOfProduct(orderId, productId, amount);
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(orderId));
        verify(productService, times(1)).findById(Long.parseLong(productId));
    }

    @Test
    public void testMakeStatusClosedShouldNotThrowExceptionsWhenOrderWithThisIdExists() {
        String id = "1";
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        doNothing().when(orderDao).changeStatusToClosed(Long.parseLong(id), Order.OrderStatus.CLOSED);
        orderService.makeStatusClosed(id);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).changeStatusToClosed(Long.parseLong(id), Order.OrderStatus.CLOSED);
    }

    @Test(expected = InvalidIdException.class)
    public void testMakeStatusClosedShouldThrowInvalidIdExceptionWhenOrderWithThisIdAbsent() {
        String id = "2";
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        doThrow(new NumberFormatException()).when(orderDao).changeStatusToClosed(Long.parseLong(id), Order.OrderStatus.CLOSED);
        orderService.makeStatusClosed(id);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).changeStatusToClosed(Long.parseLong(id), Order.OrderStatus.CLOSED);
    }

    @Test
    public void testCancelOrderShouldNotThrowExceptionsWhenOrderWithThisIdExist() {
        String id = "1";
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 1L));
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(id))).thenReturn(Optional.of(orderForTest));
        doNothing().when(orderDao).update(any(Order.class));
        orderService.cancelOrder(id);
        assertEquals(Order.OrderStatus.CANCELED, orderForTest.getStatus());
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(id));
        verify(orderDao, times(1)).update(any(Order.class));
    }

    @Test
    public void testCancelProductShouldNotThrowExceptionsWhenAllIdsAreValid() {
        String orderId = "1";
        String productId = "1";
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 1L));
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findById(Long.parseLong(orderId))).thenReturn(Optional.of(orderForTest));
        when(productService.findById(Long.parseLong(productId))).thenReturn(Optional.of(productForTest));
        doNothing().when(orderDao).updateWithRelations(any(Order.class));
        doNothing().when(productService).updateProduct(any(Product.class));
        orderService.cancelProduct(orderId, productId);
        assertEquals(0L, (long)orderForTest.getOrderProducts().stream().findFirst().get().getAmount());
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findById(Long.parseLong(orderId));
        verify(productService, times(1)).findById(Long.parseLong(productId));
        verify(orderDao, times(1)).updateWithRelations(any(Order.class));
        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test(expected = InvalidIdException.class)
    public void testGetProductByIdShouldThrowInvalidIdExceptionWhenIdIsInvalid() {
        String invalidId = "dwqfw";
        orderService.getProductById(invalidId);
    }

    @Test
    public void testMakeXReportShouldReturnReportDTOWhenOrderDaoDoNotGenerateExceptions() {
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 1L));
        ReportDTO expectedReportDTO = ReportDTO.builder()
                .amount(1L)
                .totalPrice(100L)
                .build();
        List<Order> orders = List.of(orderForTest);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findByStatus(Order.OrderStatus.CLOSED)).thenReturn(orders);
        ReportDTO reportDTO = orderService.makeXReport();
        assertEquals(expectedReportDTO, reportDTO);
        verify(daoFactory, times(1)).createOrderDao();
        verify(orderDao, times(1)).findByStatus(Order.OrderStatus.CLOSED);
    }

    @Test
    public void testMakeZReportShouldReturnReportDTOAndArchiveOrdersWhenOrderDaoDoNotGenerateExceptions() {
        Order orderForTest = Order.builder()
                .id(1L)
                .status(Order.OrderStatus.NEW)
                .totalPrice(100L)
                .createDate(localDateTime)
                .user(user)
                .orderProducts(new HashSet<>())
                .build();
        Product productForTest = Product.builder()
                .id(1L)
                .code("1234")
                .name("Banana")
                .price(100)
                .measure(Product.Measure.valueOf("BY_WEIGHT"))
                .amount(4000L)
                .build();
        orderForTest.getOrderProducts().add(new OrderProducts(orderForTest, productForTest, 1L));
        ReportDTO expectedReportDTO = ReportDTO.builder()
                .amount(1L)
                .totalPrice(100L)
                .build();
        List<Order> orders = List.of(orderForTest);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findByStatus(Order.OrderStatus.CLOSED)).thenReturn(orders);
        doNothing().when(orderDao).update(orderForTest);
        ReportDTO reportDTO = orderService.makeZReport();
        assertEquals(expectedReportDTO, reportDTO);
        assertEquals(Order.OrderStatus.ARCHIVED, orderForTest.getStatus());
        verify(daoFactory, times(2)).createOrderDao();
        verify(orderDao, times(1)).findByStatus(Order.OrderStatus.CLOSED);
        verify(orderDao, times(1)).update(any(Order.class));
    }
}
