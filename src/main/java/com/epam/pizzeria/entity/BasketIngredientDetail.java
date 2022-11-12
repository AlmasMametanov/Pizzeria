package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.Objects;

public class BasketIngredientDetail implements Serializable {
    private Long id;
    private Long basketId;
    private Long additionalIngredientDetailId;
    private AdditionalIngredient additionalIngredient;
    private ProductSizeIngredientDetail productSizeIngredientDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Long getAdditionalIngredientDetailId() {
        return additionalIngredientDetailId;
    }

    public void setAdditionalIngredientDetailId(Long additionalIngredientDetailId) {
        this.additionalIngredientDetailId = additionalIngredientDetailId;
    }

    public AdditionalIngredient getAdditionalIngredient() {
        return additionalIngredient;
    }

    public void setAdditionalIngredient(AdditionalIngredient additionalIngredient) {
        this.additionalIngredient = additionalIngredient;
    }

    public ProductSizeIngredientDetail getProductSizeIngredientDetail() {
        return productSizeIngredientDetail;
    }

    public void setProductSizeIngredientDetail(ProductSizeIngredientDetail productSizeIngredientDetail) {
        this.productSizeIngredientDetail = productSizeIngredientDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketIngredientDetail that = (BasketIngredientDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(basketId, that.basketId) &&
                Objects.equals(additionalIngredientDetailId, that.additionalIngredientDetailId) &&
                Objects.equals(productSizeIngredientDetail, that.productSizeIngredientDetail) &&
                Objects.equals(additionalIngredient, that.additionalIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basketId, additionalIngredientDetailId, additionalIngredient, productSizeIngredientDetail);
    }

    @Override
    public String toString() {
        return "BasketIngredientDetail{" +
                "id=" + id +
                ", basketId=" + basketId +
                ", additionalIngredientDetailId=" + additionalIngredientDetailId +
                ", productSizeIngredientDetail=" + productSizeIngredientDetail +
                ", additionalIngredient=" + additionalIngredient +
                '}';
    }
}
