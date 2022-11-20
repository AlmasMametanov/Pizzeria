package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProductSize implements Serializable {
    private Long id;
    private String name;
    private String size;
    private ProductSizeDetail productSizeDetail;
    private ProductSizeIngredientDetail productSizeIngredientDetail;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ProductSizeDetail getProductSizeDetail() {
        return productSizeDetail;
    }

    public void setProductSizeDetail(ProductSizeDetail productSizeDetail) {
        this.productSizeDetail = productSizeDetail;
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
        ProductSize productSize = (ProductSize) o;
        return Objects.equals(id, productSize.id) &&
                Objects.equals(name, productSize.name) &&
                Objects.equals(size, productSize.size) &&
                Objects.equals(productSizeDetail, productSize.productSizeDetail) &&
                Objects.equals(productSizeIngredientDetail, productSize.productSizeIngredientDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size, productSizeDetail, productSizeIngredientDetail);
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", productSizeDetail='" + productSizeDetail + '\'' +
                ", productSizeIngredientDetail='" + productSizeIngredientDetail + '\'' +
                '}';
    }
}
