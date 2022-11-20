package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class AdditionalIngredient implements Serializable {
    private Long id;
    private String name;
    private String imageUrl;
    private Boolean isActive;
    private List<ProductSize> productSizeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<ProductSize> getProductSizeList() {
        return productSizeList;
    }

    public void setProductSizeList(List<ProductSize> productSizeList) {
        this.productSizeList = productSizeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalIngredient that = (AdditionalIngredient) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(productSizeList, that.productSizeList) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, productSizeList, isActive);
    }

    @Override
    public String toString() {
        return "AdditionalIngredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl=" + imageUrl +
                ", productSizeList=" + productSizeList +
                ", isActive=" + isActive +
                '}';
    }
}
