package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Product implements Serializable {
    private Long id;
    private Long productCategoryId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private Boolean isPizza;
    private Boolean isActive;
    private ProductSize productSize;
    private List<ProductSizeDetail> productSizeDetailList;
    private List<Long> productIngredientDetailList;
    private List<BasketIngredientDetail> basketIngredientDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getIsPizza() {
        return isPizza;
    }

    public void setIsPizza(Boolean isPizza) {
        this.isPizza = isPizza;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public List<BasketIngredientDetail> getBasketIngredientDetailList() {
        return basketIngredientDetailList;
    }

    public void setBasketIngredientDetailList(List<BasketIngredientDetail> basketIngredientDetailList) {
        this.basketIngredientDetailList = basketIngredientDetailList;
    }

    public List<ProductSizeDetail> getProductSizeDetailList() {
        return productSizeDetailList;
    }

    public void setProductSizeDetailList(List<ProductSizeDetail> productSizeDetailList) {
        this.productSizeDetailList = productSizeDetailList;
    }

    public List<Long> getProductIngredientDetailList() {
        return productIngredientDetailList;
    }

    public void setProductIngredientDetailList(List<Long> productIngredientDetailList) {
        this.productIngredientDetailList = productIngredientDetailList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(productCategoryId, product.productCategoryId) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imageUrl, product.imageUrl) &&
                Objects.equals(price, product.price) &&
                Objects.equals(isPizza, product.isPizza) &&
                Objects.equals(isActive, product.isActive) &&
                Objects.equals(basketIngredientDetailList, product.basketIngredientDetailList) &&
                Objects.equals(productSizeDetailList, product.productSizeDetailList) &&
                Objects.equals(productIngredientDetailList, product.productIngredientDetailList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCategoryId, name, description, imageUrl, price, isPizza, isActive, productIngredientDetailList, productSizeDetailList, basketIngredientDetailList);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCategoryId=" + productCategoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", isPizza=" + isPizza +
                ", isActive=" + isActive +
                ", basketIngredientDetailList=" + basketIngredientDetailList +
                ", productSizeDetailList=" + productSizeDetailList +
                ", productIngredientDetailList=" + productIngredientDetailList +
                '}';
    }
}
