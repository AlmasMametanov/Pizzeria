package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class OrderDetail implements Serializable {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer count;
    private Integer price;
    private Long productSizeId;
    private Product product;
    private ProductSize productSize;
    private List<OrderIngredientDetail> orderIngredientDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(Long productSizeId) {
        this.productSizeId = productSizeId;
    }

    public List<OrderIngredientDetail> getOrderIngredientDetailList() {
        return orderIngredientDetailList;
    }

    public void setOrderIngredientDetailList(List<OrderIngredientDetail> orderIngredientDetailList) {
        this.orderIngredientDetailList = orderIngredientDetailList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(count, that.count) &&
                Objects.equals(price, that.price) &&
                Objects.equals(productSizeId, that.productSizeId) &&
                Objects.equals(orderIngredientDetailList, that.orderIngredientDetailList) &&
                Objects.equals(product, that.product) &&
                Objects.equals(productSize, that.productSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productId, count, price, productSizeId, orderIngredientDetailList, product, productSize);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", count=" + count +
                ", price=" + price +
                ", pizzaSizeId=" + productSizeId +
                ", orderIngredientDetailList=" + orderIngredientDetailList +
                ", product=" + product +
                ", productSize=" + productSize +
                '}';
    }
}
