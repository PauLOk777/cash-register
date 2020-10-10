package com.paulok777.model.dao.mapper;

import com.paulok777.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phoneNumber"))
                .role(User.Role.valueOf(rs.getString("role")))
                .build();
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
