package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProductCategoryLocale implements Serializable {
    private Long id;
    private Long localeId;
    private Long productCategoryId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Long localeId) {
        this.localeId = localeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryLocale that = (ProductCategoryLocale) o;
        return Objects.equals(id, that.id) && Objects.equals(localeId, that.localeId) && Objects.equals(productCategoryId, that.productCategoryId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, productCategoryId, name);
    }

    @Override
    public String toString() {
        return "ProductCategoryLocale{" +
                "id=" + id +
                ", localeId=" + localeId +
                ", productCategoryId=" + productCategoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
