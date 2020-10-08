package com.paulok777.model.dto;

public class ProductDTO {
    private String code;
    private String name;
    private int price;
    private long amount;
    private String measure;

    public static ProductDTO.ProductDTOBuilder builder() {
        return new ProductDTO.ProductDTOBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public long getAmount() {
        return this.amount;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public void setAmount(final long amount) {
        this.amount = amount;
    }

    public void setMeasure(final String measure) {
        this.measure = measure;
    }

    public String toString() {
        return "ProductDTO(code=" + code + ", name=" + name + ", price=" + price +
                ", amount=" + amount + ", measure=" + measure + ")";
    }

    public ProductDTO(final String code, final String name, final int price, final long amount, final String measure) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.measure = measure;
    }

    public ProductDTO() {
    }

    public static class ProductDTOBuilder {
        private String code;
        private String name;
        private int price;
        private long amount;
        private String measure;

        ProductDTOBuilder() {
        }

        public ProductDTO.ProductDTOBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public ProductDTO.ProductDTOBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ProductDTO.ProductDTOBuilder price(final int price) {
            this.price = price;
            return this;
        }

        public ProductDTO.ProductDTOBuilder amount(final long amount) {
            this.amount = amount;
            return this;
        }

        public ProductDTO.ProductDTOBuilder measure(final String measure) {
            this.measure = measure;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(code, name, price, amount, measure);
        }

        public String toString() {
            return "ProductDTO.ProductDTOBuilder(code=" + code + ", name=" + name + ", price=" + price +
                    ", amount=" + amount + ", measure=" + measure + ")";
        }
    }
}
