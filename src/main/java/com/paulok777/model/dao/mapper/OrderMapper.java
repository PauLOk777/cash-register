package com.paulok777.model.dao.mapper;

import com.paulok777.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }
}
