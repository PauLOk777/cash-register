package com.paulok777.model.dao.mapper;

import com.paulok777.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProductMapper implements ObjectMapper<Product> {
    @Override
    public Product extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return Product.builder()
                .id(rs.getLong("product_id"))
                .code(rs.getString("code"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .measure(Product.Measure.valueOf(rs.getString("measure")))
                .amount(rs.getLong("amount"))
                .build();
    }

    @Override
    public Product makeUnique(Map<Long, Product> cache, Product entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
