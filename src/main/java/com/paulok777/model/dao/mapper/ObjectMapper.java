package com.paulok777.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T> {
    T extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException;
}
