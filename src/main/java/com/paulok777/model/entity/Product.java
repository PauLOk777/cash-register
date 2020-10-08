package com.paulok777.model.entity;

import com.paulok777.model.dto.ProductDTO;

import java.util.Objects;
import java.util.Set;

public class Product {
    private Long id;
    private String code;
    private String name;
    private Integer price;
    private Long amount;
    private Measure measure;

    public Product(ProductDTO productDTO) {
        code = productDTO.getCode();
        name = productDTO.getName();
        price = productDTO.getPrice();
        amount = productDTO.getAmount();
        measure = Measure.valueOf(productDTO.getMeasure());
    }

    public Product(Long id, String code, String name, Integer price, Long amount, Measure measure) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.measure = measure;
    }

    public enum Measure {
        BY_WEIGHT,
        BY_QUANTITY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(code, product.code) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(amount, product.amount) &&
                measure == product.measure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, price, amount, measure);
    }
}
