package com.paulok777.model.dao.impl.query;

public class UserQueries {
    public static final String FIND_ALL = "select * from users";
    public static final String FIND_BY_ID = FIND_ALL + " where id = ?";
    public static final String FIND_BY_USERNAME = FIND_ALL + " where username = ?";
    public static final String CREATE = "insert into users (first_name, last_name, username, password, phone_number, email, role)" +
            " values (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE  = "update users set first_name = ?, last_name = ?, username = ?, " +
            "password = ?, email = ?, phoneNumber = ?, role = ? where id = ?";
    public static final String DELETE = "delete from users where id = ?";
}
