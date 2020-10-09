package com.paulok777.model.entity;

import java.util.Objects;

public class OrderProducts {
    private Order order;
    private Product product;
    Long amount;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProducts that = (OrderProducts) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product, amount);
    }
}
