package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.ProductSizeIngredientDetail;

import java.util.List;

public interface ProductSizeIngredientDetailDAO {
    void insertProductSizeIngredientDetail(ProductSizeIngredientDetail productSizeIngredientDetail);
    List<ProductSizeIngredientDetail> getAllProductSizeIngredientDetailBySizeId(Long sizeId);
}
