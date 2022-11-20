package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.ProductCategory;

public interface ProductCategoryDAO {
    void insertProductCategory(ProductCategory productCategory);
    void removeProductCategory(Long productCategoryId);
}
