package com.paulok777.model.dao.mapper;

import com.paulok777.model.entity.Order;
import com.paulok777.model.entity.OrderProducts;
import com.paulok777.model.entity.Product;
import com.paulok777.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {
    private final UserMapper userMapper = new UserMapper();
    private final ProductMapper productMapper = new ProductMapper();

    @Override
    public Order extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getLong("order_id"))
                .createDate(rs.getTimestamp("create_date").toLocalDateTime())
                .status(Order.OrderStatus.valueOf(rs.getString("status")))
                .totalPrice(rs.getLong("total_price"))
                .build();
    }

    public Order extractWithRelationsFromResultSet(ResultSet rs, Map<Long, Order> ordersCache,
                                                   Map<Long, User> usersCache,
                                                   Map<Long, Product> productsCache) throws SQLException {
        Order order = makeUnique(ordersCache, extractWithoutRelationsFromResultSet(rs));
        order.setUser(userMapper.makeUnique(usersCache, userMapper.extractWithoutRelationsFromResultSet(rs)));
        if (rs.getString("measure") != null) {
            Product product = productMapper.makeUnique(productsCache, productMapper.extractWithoutRelationsFromResultSet(rs));
            order.getOrderProducts().add(extractOrderProductsFromResultSet(rs, order, product));
        }
        return order;
    }

    private OrderProducts extractOrderProductsFromResultSet(ResultSet rs, Order order, Product product) throws SQLException {
        return new OrderProducts(order, product, rs.getLong("amount_in_order"));
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
