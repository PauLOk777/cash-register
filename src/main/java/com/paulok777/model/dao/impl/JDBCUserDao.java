package com.paulok777.model.dao.impl;

import com.paulok777.model.dao.UserDao;
import com.paulok777.model.dao.impl.query.UserQueries;
import com.paulok777.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void create(User entity) {
//        try (PreparedStatement ps = connection.prepareStatement(UserQueries.CREATE)) {
//
//        } catch (SQLException e) {
//
//        }
    }

    @Override
    public Optional<User> findById(long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(long id) {

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
