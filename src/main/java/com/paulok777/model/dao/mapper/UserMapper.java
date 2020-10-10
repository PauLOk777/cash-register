package com.paulok777.model.dao.mapper;

import com.paulok777.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }
}
