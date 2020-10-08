package com.paulok777.model.dao;

import com.paulok777.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByUsername(String username);
}
