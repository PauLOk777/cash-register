package com.paulok777.model.dao.impl.query;

public class OrderQueries {
    public static final String FIND_ALL = "select * from orders o";
    public static final String CREATE = "insert into orders (create_date, status, total_price, user_id) values (?, ?, ?, ?)";
    public static final String FIND_BY_ID = FIND_ALL + " where order_id = ?";
    public static final String FIND_BY_STATUS = FIND_ALL + " where status = ?";
    public static final String FIND_BY_STATUS_ORDER_BY_DATE_DESC = FIND_ALL + " where status = ? order by create_date desc";
    public static final String UPDATE = "update orders set create_date = ?, status = ?, total_price = ? where order_id = ?";
    public static final String DELETE = "delete from orders where order_id = ?";
    public static final String CHANGE_STATUS_TO_CLOSED = "update orders set status = ? where order_id = ?";
    public static final String FIND_ALL_WITH_RELATIONSHIPS = "select * from orders o left join order_products op using(order_id)" +
            " left join products p using(product_id) join users u using(user_id)";
    public static final String UPDATE_PRODUCT_AMOUNT_IN_ORDER = "update order_products set amount_in_order = ?" +
            " where order_id = ? and product_id = ?";
    public static final String ADD_PRODUCT_TO_ORDER = "insert into order_products (order_id, product_id, amount_in_order) values(?, ?, ?)";
    public static final String FIND_BY_ID_WITH_RELATIONS = FIND_ALL_WITH_RELATIONSHIPS + " where order_id = ?";
    public static final String FIND_BY_STATUS_WITH_RELATIONS = FIND_ALL_WITH_RELATIONSHIPS + " where status = ?";
    public static final String FIND_BY_STATUS_ORDER_BY_DATE_DESC_WITH_RELATIONS = FIND_ALL_WITH_RELATIONSHIPS + " where order_id = ?";
}
