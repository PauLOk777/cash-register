package com.paulok777.model.dao.impl;

import com.paulok777.model.dao.UserDao;
import com.paulok777.model.dao.impl.query.UserQueries;
import com.paulok777.model.dao.mapper.UserMapper;
import com.paulok777.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private final Connection connection;
    private final UserMapper userMapper = new UserMapper();

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(UserQueries.FIND_BY_USERNAME)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = userMapper.extractWithoutRelationsFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void create(User entity) {
        try (PreparedStatement ps = connection.prepareStatement(UserQueries.CREATE)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getUsername());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getPhoneNumber());
            ps.setString(6, entity.getEmail());
            ps.setString(7, entity.getRole().getAuthority());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<User> findById(long id) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(UserQueries.FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = userMapper.extractWithoutRelationsFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(UserQueries.FIND_ALL);
            while (rs.next()) {
                users.add(userMapper.extractWithoutRelationsFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement statement = connection.prepareStatement(UserQueries.UPDATE)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getUsername());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getEmail());
            statement.setString(6, entity.getPhoneNumber());
            statement.setString(7, entity.getRole().getAuthority());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(UserQueries.DELETE)) {
            statement.setLong(1, id);
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
