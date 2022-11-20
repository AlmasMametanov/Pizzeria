package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.ProductCategoryLocale;

import java.util.List;

public interface ProductCategoryLocaleDAO {
    void insertProductCategoryLocale(ProductCategoryLocale productCategoryLocale);
    List<ProductCategoryLocale> getAllProductCategoryLocale(Long localeId);
    void removeProductCategoryLocale(Long productCategoryId);
}
