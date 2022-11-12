package com.epam.pizzeria.entity;

import java.util.Objects;

public class ProductSizeDetail {
    private Long id;
    private Long productSizeId;
    private Long productId;
    private Integer price;
    private ProductSize productSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(Long productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSizeDetail that = (ProductSizeDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productSizeId, that.productSizeId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(productSize, that.productSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productSizeId, productId, price, productSize);
    }

    @Override
    public String toString() {
        return "ProductSizeDetail{" +
                "id=" + id +
                ", productSizeId=" + productSizeId +
                ", productId=" + productId +
                ", price=" + price +
                ", productSize=" + productSize +
                '}';
    }
}
