package com.paulok777.model.dao;

import com.paulok777.model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    void changeStatusToClosed(Long id, Order.OrderStatus orderStatus);

    List<Order> findByStatusOrderByCreateDateDesc(Order.OrderStatus status);

    List<Order> findByStatus(Order.OrderStatus status);
}
