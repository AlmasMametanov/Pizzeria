package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.Product;
import com.epam.pizzeria.entity.ProductCategoryLocale;

import java.util.List;

public interface ProductCategoryLocaleDAO {
    void insertProductCategoryLocale(ProductCategoryLocale productCategoryLocale);
    List<ProductCategoryLocale> getAllProductCategoryLocale(Long localeId);
    String getProductCategoryName(Long productCategoryId, Long localeId);
    void removeProductCategoryLocale(Long productCategoryId);
}
