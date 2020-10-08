package com.paulok777.model.dao.impl;

import com.paulok777.model.dao.OrderDao;
import com.paulok777.model.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void changeStatusToClosed(Long id, Order.OrderStatus orderStatus) {

    }

    @Override
    public List<Order> findByStatusOrderByCreateDateDesc(Order.OrderStatus status) {
        return null;
    }

    @Override
    public List<Order> findByStatus(Order.OrderStatus status) {
        return null;
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
