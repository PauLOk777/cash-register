package com.paulok777.model.dao.impl;

import com.paulok777.model.dao.ProductDao;
import com.paulok777.model.dao.impl.query.ProductQueries;
import com.paulok777.model.dao.mapper.ProductMapper;
import com.paulok777.model.entity.Product;
import com.paulok777.model.util.Page;
import com.paulok777.model.util.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCProductDao implements ProductDao {
    private final Connection connection;
    private final ProductMapper productMapper = new ProductMapper();

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void updateAmountById(Long amount, Long id) {
        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.UPDATE_AMOUNT)) {
            statement.setLong(1, amount);
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Product> findByIdentifier(String identifier) {
        Product product = null;
        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.FIND_BY_IDENTIFIER)) {
            statement.setString(1, identifier);
            statement.setString(2, identifier);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                product = productMapper.extractWithoutRelationsFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.ofNullable(product);
    }

    @Override
    public Page<Product> findByOrderByName(Pageable pageable) {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(ProductQueries.FIND_ALL_ORDER_BY_NAME);
            while (rs.next()) {
                products.add(productMapper.extractWithoutRelationsFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        int firstProductInPageIndex = pageable.getSizeOfPage() * pageable.getCurrentPage();
        int lastProductInPageIndex = Math.min(products.size(), firstProductInPageIndex + pageable.getSizeOfPage());

        List<Product> productsForPage = products.subList(firstProductInPageIndex, lastProductInPageIndex);
        return new Page<>((int) Math.ceil((double)products.size() / pageable.getSizeOfPage()), productsForPage);
    }

    @Override
    public void create(Product entity) {
        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.CREATE)) {
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getPrice());
            statement.setString(4, entity.getMeasure().name());
            statement.setLong(5, entity.getAmount());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        Product product = null;
        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                product = productMapper.extractWithoutRelationsFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(ProductQueries.FIND_ALL);
            while (rs.next()) {
                products.add(productMapper.extractWithoutRelationsFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return products;
    }

    @Override
    public void update(Product entity) {
        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.UPDATE)) {
            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getPrice());
            statement.setString(4, entity.getMeasure().name());
            statement.setLong(5, entity.getAmount());
            statement.setLong(6, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
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
