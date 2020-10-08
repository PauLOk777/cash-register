package com.paulok777.model.dao.impl;

import com.paulok777.model.dao.ProductDao;
import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;
import com.paulok777.model.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCProductDao implements ProductDao {
    private final Connection connection;

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void updateAmountById(Long amount, Long id) {

    }

    @Override
    public Optional<Product> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Page<Product> findByOrderByName(Pageable pageable) {
        return null;
    }

    @Override
    public void create(Product entity) {

    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public void update(Product entity) {

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
