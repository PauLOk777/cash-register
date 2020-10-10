package com.paulok777.model.dao.impl.query;

public class OrderQueries {
    public static final String FIND_ALL = "select * from orders";
    public static final String CREATE = "insert into orders (create_date, status, total_price, user_id) values (?, ?, ?, ?)";
    public static final String FIND_BY_ID = FIND_ALL + " where id = ?";
    public static final String FIND_BY_STATUS = FIND_ALL + " where status = ?";
    public static final String FIND_BY_STATUS_ORDER_BY_DATE_DESC = FIND_ALL + " where status = ? order by create_date desc";
    public static final String UPDATE = "update orders set create_date = ?, status = ?, total_price = ? where id = ?";
    public static final String DELETE = "delete from orders where id = ?";
    public static final String CHANGE_STATUS_TO_CLOSED = "update orders set status = ? where id = ?";
    public static final String FIND_ALL_WITH_RELATIONSHIPS = "select * from orders o join order_products op on o.id = op.order_id" +
            " join products p on p.id = op.product_id join users u on u.id = o.user_id";
    public static final String UPDATE_PRODUCT_AMOUNT_IN_ORDER = "update order_products set amount = ?" +
            " where order_id = ? and product_id = ?";
    public static final String ADD_PRODUCT_TO_ORDER = "insert into order_products (order_id, product_id, amount) values(?, ?, ?)";
    public static final String FIND_BY_ID_WITH_RELATIONS = FIND_ALL_WITH_RELATIONSHIPS + " where id = ?";
    public static final String FIND_BY_STATUS_WITH_RELATIONS = FIND_ALL_WITH_RELATIONSHIPS + " where status = ?";
    public static final String FIND_BY_STATUS_ORDER_BY_DATE_DESC_WITH_RELATIONS = FIND_ALL_WITH_RELATIONSHIPS + " where id = ?";
}
