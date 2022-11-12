package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.ProductIngredientDetail;

import java.util.List;

public interface ProductIngredientDetailDAO {
    void insertProductIngredientDetail(ProductIngredientDetail productIngredientDetail);
    List<Long> getAllProductIngredientDetailByProductId(Long productId);
    void deleteProductIngredientDetail(Long ingredientId, Long productId);
}
