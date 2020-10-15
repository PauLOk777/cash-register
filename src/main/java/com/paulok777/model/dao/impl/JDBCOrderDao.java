package com.paulok777.model.dao.impl;

import com.paulok777.model.dao.OrderDao;
import com.paulok777.model.dao.impl.query.OrderQueries;
import com.paulok777.model.dao.mapper.OrderMapper;
import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.OrderProducts;
import com.paulok777.model.entity.Product;
import com.paulok777.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;
    private final OrderMapper orderMapper = new OrderMapper();
    private static final Logger logger = LogManager.getLogger(JDBCOrderDao.class);

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void changeStatusToClosed(Long id, Order.OrderStatus orderStatus) {
        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.CHANGE_STATUS_TO_CLOSED)) {
            statement.setString(1, orderStatus.name());
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            logger.error("{}, when trying change status of order (id: {}) to closed", e.getMessage(), id);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Order> findByStatusOrderByCreateDateDesc(Order.OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.FIND_BY_STATUS_ORDER_BY_DATE_DESC)) {
            statement.setString(1, status.name());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(orderMapper.extractWithoutRelationsFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("{}, when trying to find new orders by create date desc without relations", e.getMessage());
            throw new RuntimeException();
        }
        return orders;
    }

    @Override
    public List<Order> findByStatus(Order.OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.FIND_BY_STATUS)) {
            statement.setString(1, status.name());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(orderMapper.extractWithoutRelationsFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("{}, when trying to find new orders without relations", e.getMessage());
            throw new RuntimeException();
        }
        return orders;
    }

    @Override
    public long createAndGetNewId(Order order) {
        try (PreparedStatement statement = connection.prepareStatement(
                OrderQueries.CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, Timestamp.valueOf(order.getCreateDate()));
            statement.setString(2, order.getStatus().name());
            statement.setLong(3, order.getTotalPrice());
            statement.setLong(4, order.getUser().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("{}, when trying create new order and get new id", e.getMessage());
            throw new RuntimeException();
        }
        logger.error("Some problems with getting new id.");
        throw new RuntimeException();
    }

    @Override
    public void create(Order entity) {
        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.CREATE)) {
            statement.setTimestamp(1, Timestamp.valueOf(entity.getCreateDate()));
            statement.setString(2, entity.getStatus().name());
            statement.setLong(3, entity.getTotalPrice());
            statement.setLong(4, entity.getUser().getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error("{}, when trying create new order", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = null;
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Product> products = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.FIND_BY_ID_WITH_RELATIONS)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order = orderMapper.extractWithRelationsFromResultSet(rs, orders, users, products);
            }
        } catch (SQLException e) {
            logger.error("{}, when trying to get order (id: {}) by id with relations", e.getMessage(), id);
            throw new RuntimeException();
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Product> products = new HashMap<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(OrderQueries.FIND_ALL_WITH_RELATIONSHIPS);
            while (rs.next()) {
                orderMapper.extractWithRelationsFromResultSet(rs, orders, users, products);
            }
        } catch (SQLException e) {
            logger.error("{}, when trying to find all orders", e.getMessage());
            throw new RuntimeException();
        }

        return new ArrayList<>(orders.values());
    }

    @Override
    public void update(Order entity) {
        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.UPDATE)) {
            statement.setTimestamp(1, Timestamp.valueOf(entity.getCreateDate()));
            statement.setString(2, entity.getStatus().name());
            statement.setLong(3, entity.getTotalPrice());
            statement.setLong(4, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error("{}, when trying to update order", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void updateWithRelations(Order order) {
        try (PreparedStatement orderStatement = connection.prepareStatement(OrderQueries.UPDATE)) {
            connection.setAutoCommit(false);
            orderStatement.setTimestamp(1, Timestamp.valueOf(order.getCreateDate()));
            orderStatement.setString(2, order.getStatus().name());
            orderStatement.setLong(3, order.getTotalPrice());
            orderStatement.setLong(4, order.getId());

            try (PreparedStatement orderProductsStatement =
                         connection.prepareStatement(OrderQueries.UPDATE_PRODUCT_AMOUNT_IN_ORDER)) {
                for (OrderProducts orderProducts: order.getOrderProducts()) {
                    orderProductsStatement.setLong(1, orderProducts.getAmount());
                    orderProductsStatement.setLong(2, orderProducts.getOrder().getId());
                    orderProductsStatement.setLong(3, orderProducts.getProduct().getId());
                    if (orderProductsStatement.executeUpdate() == 0) {
                        try (PreparedStatement insertStatement =
                                     connection.prepareStatement(OrderQueries.ADD_PRODUCT_TO_ORDER)) {
                            insertStatement.setLong(1, orderProducts.getOrder().getId());
                            insertStatement.setLong(2, orderProducts.getProduct().getId());
                            insertStatement.setLong(3, orderProducts.getAmount());
                            insertStatement.execute();
                        }
                    }
                }
            }

            orderStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                logger.error("{}, when trying to update order with relations", e.getMessage());
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("{}, when trying to rollback", e.getMessage());
            }
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(OrderQueries.DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            logger.error("{}, when trying to delete order (id: {})", e.getMessage(), id);
            throw new RuntimeException();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("{}, when trying to close connection", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
