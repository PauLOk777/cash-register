package com.paulok777.model.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Order {
    private Long id;
    private Long totalPrice;
    private LocalDateTime createDate;
    private OrderStatus status;
    private User user;
    private Set<OrderProducts> orderProducts = new HashSet<>();

    public enum OrderStatus {
        NEW,
        CANCELED,
        CLOSED,
        ARCHIVED;
    }

    public Order(Long id, Long totalPrice, LocalDateTime createDate, OrderStatus status, User user, Set<OrderProducts> orderProducts) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.status = status;
        this.user = user;
        this.orderProducts = orderProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setProducts(Set<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(createDate, order.createDate) &&
                status == order.status &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, createDate, status, user);
    }

    public static Order.OrderBuilder builder() {
        return new Order.OrderBuilder();
    }

    public static class OrderBuilder {
        private Long id;
        private Long totalPrice;
        private LocalDateTime createDate;
        private OrderStatus status;
        private User user;
        private Set<OrderProducts> orderProducts;

        OrderBuilder() {
        }

        public Order.OrderBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Order.OrderBuilder totalPrice(final Long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Order.OrderBuilder createDate(final LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Order.OrderBuilder status(final OrderStatus status) {
            this.status = status;
            return this;
        }

        public Order.OrderBuilder user(final User user) {
            this.user = user;
            return this;
        }

        public Order.OrderBuilder orderProducts(final Set<OrderProducts> orderProducts) {
            this.orderProducts = orderProducts;
            return this;
        }

        public Order build() {
            return new Order(this.id, this.totalPrice, this.createDate, this.status, this.user, this.orderProducts);
        }
    }
}
