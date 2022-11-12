package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.Objects;

public class Basket implements Serializable {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private Long productSizeId;
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(Long productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(id, basket.id) &&
                Objects.equals(userId, basket.userId) &&
                Objects.equals(productId, basket.productId) &&
                Objects.equals(count, basket.count) &&
                Objects.equals(productSizeId, basket.productSizeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, count, productSizeId);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", count=" + count +
                ", productSizeId=" + productSizeId +
                '}';
    }
}
