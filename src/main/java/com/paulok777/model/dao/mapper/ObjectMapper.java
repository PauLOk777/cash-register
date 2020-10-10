package com.paulok777.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ObjectMapper<T> {
    T extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException;

    T makeUnique(Map<Long, T> cache, T entity);
}
