package com.epam.pizzeria.entity;

import java.util.Objects;

public class ProductSizeIngredientDetail {
    private Long id;
    private Long sizeId;
    private Long ingredientId;
    private Integer price;
    private AdditionalIngredient additionalIngredient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public AdditionalIngredient getAdditionalIngredient() {
        return additionalIngredient;
    }

    public void setAdditionalIngredient(AdditionalIngredient additionalIngredient) {
        this.additionalIngredient = additionalIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSizeIngredientDetail that = (ProductSizeIngredientDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sizeId, that.sizeId) &&
                Objects.equals(ingredientId, that.ingredientId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(additionalIngredient, that.additionalIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sizeId, ingredientId, price, additionalIngredient);
    }

    @Override
    public String toString() {
        return "ProductSizeIngredientDetail{" +
                "id=" + id +
                ", sizeId=" + sizeId +
                ", ingredientId=" + ingredientId +
                ", price=" + price +
                ", additionalIngredient=" + additionalIngredient +
                '}';
    }
}
