package com.epam.pizzeria.entity;

import java.util.Objects;

public class ProductIngredientDetail {
    private Long id;
    private Long ingredientId;
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductIngredientDetail that = (ProductIngredientDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ingredientId, that.ingredientId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredientId, productId);
    }

    @Override
    public String toString() {
        return "ProductIngredientDetail{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", productId=" + productId +
                '}';
    }
}
