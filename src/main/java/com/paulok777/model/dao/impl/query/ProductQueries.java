package com.paulok777.model.dao.impl.query;

public class ProductQueries {
    public static final String FIND_ALL = "select * from products";
    public static final String CREATE = "insert into products (code, name, price, measure, amount) values (?, ?, ?, ?, ?)";
    public static final String FIND_BY_ID = FIND_ALL + " where id = ?";
    public static final String FIND_BY_IDENTIFIER = FIND_ALL + " where code = ? or name = ?";
    public static final String FIND_ALL_ORDER_BY_NAME = FIND_ALL + " order by name";
    public static final String UPDATE = "update products set amount = ? where id = ?";
    public static final String DELETE = "delete from products where id = ?";
}
