package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.ProductSize;

import java.util.List;

public interface ProductSizeDAO {
    ProductSize getProductSizeBySizeIdAndProductId(Long productSizeId, Long productId);
    List<ProductSize> getAllProductSize();
    List<ProductSize> getAllProductSizeByIngredientId(Long ingredientId);

}
