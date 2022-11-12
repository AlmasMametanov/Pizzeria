package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderIngredientDetail implements Serializable {
    private Long id;
    private Long orderDetailId;
    private Long additionalIngredientDetailId;
    private ProductSizeIngredientDetail productSizeIngredientDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getAdditionalIngredientDetailId() {
        return additionalIngredientDetailId;
    }

    public void setAdditionalIngredientDetailId(Long additionalIngredientDetailId) {
        this.additionalIngredientDetailId = additionalIngredientDetailId;
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
        OrderIngredientDetail that = (OrderIngredientDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderDetailId, that.orderDetailId) &&
                Objects.equals(additionalIngredientDetailId, that.additionalIngredientDetailId) &&
                Objects.equals(productSizeIngredientDetail, that.productSizeIngredientDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDetailId, additionalIngredientDetailId, productSizeIngredientDetail);
    }

    @Override
    public String toString() {
        return "OrderIngredientDetail{" +
                "id=" + id +
                ", orderDetailId=" + orderDetailId +
                ", additionalIngredientDetailId=" + additionalIngredientDetailId +
                ", productSizeIngredientDetail=" + productSizeIngredientDetail +
                '}';
    }
}
