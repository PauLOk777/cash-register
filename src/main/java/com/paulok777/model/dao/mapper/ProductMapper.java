package com.paulok777.model.dao.mapper;

import com.paulok777.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements ObjectMapper<Product> {
    @Override
    public Product extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }
}
