package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.ProductSize;

import java.util.List;

public interface ProductSizeDAO {
    ProductSize getProductSizeBySizeIdAndProductId(Long productSizeId, Long productId);
    ProductSize getProductSizeById(Long productSizeId);
    Integer getMinPizzaSizePrice();
    Integer getPizzaSizePriceById(Long pizzaSizeId);
    List<ProductSize> getAllProductSize();
    void removePizzaSize(ProductSize productSize);
}
